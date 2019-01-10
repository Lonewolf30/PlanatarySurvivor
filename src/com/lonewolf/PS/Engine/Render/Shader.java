package com.lonewolf.PS.Engine.Render;

import com.lonewolf.PS.Engine.Component.Light;
import com.lonewolf.PS.Engine.Core.*;
import com.lonewolf.PS.Engine.Render.resourceManagement.ShaderResource;
import com.lonewolf.PS.Reference;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;

public class Shader
{
    private ShaderResource shaderResources;

    public Shader(String fileName)
    {
        shaderResources = new ShaderResource();

        String vertexShaderText = LoadShader(fileName + ".vs");
        String fragmentShaderText = LoadShader(fileName + ".fs");

        AddVertexShader(vertexShaderText);
        AddFragmentShader(fragmentShaderText);

        AddAllAttributes(vertexShaderText);

        CompileShader();

        AddAllUniforms(vertexShaderText);
        AddAllUniforms(fragmentShaderText);

    }

    private void AddAllUniforms(String shaderText)
    {
        HashMap<String, ArrayList<GLSLStruct>> structs = FindUniformStructs(shaderText);

        final String UNIFORM_KEYWORD = "uniform";
        int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
        while(uniformStartLocation != -1)
        {
            if(!(uniformStartLocation != 0
                    && (Character.isWhitespace(shaderText.charAt(uniformStartLocation - 1)) || shaderText.charAt(uniformStartLocation - 1) == ';')
                    && Character.isWhitespace(shaderText.charAt(uniformStartLocation + UNIFORM_KEYWORD.length())))) {
                uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
                continue;
            }

            int begin = uniformStartLocation + UNIFORM_KEYWORD.length() + 1;
            int end = shaderText.indexOf(";", begin);

            String uniformLine = shaderText.substring(begin, end).trim();

            int whiteSpacePos = uniformLine.indexOf(' ');
            String uniformName = uniformLine.substring(whiteSpacePos + 1, uniformLine.length()).trim();
            String uniformType = uniformLine.substring(0, whiteSpacePos).trim();

            shaderResources.GetUniformNames().add(uniformName);
            shaderResources.GetUniformTypes().add(uniformType);
            AddUniform(uniformName, uniformType, structs);

            uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
        }
    }

    private void AddAllAttributes(String shaderText)
    {
        final String ATTRIBUTE_KEYWORD = "attribute";
        int attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD);
        int attribNumber = 0;
        while(attributeStartLocation != -1)
        {
            if(!(attributeStartLocation != 0
                    && (Character.isWhitespace(shaderText.charAt(attributeStartLocation - 1)) || shaderText.charAt(attributeStartLocation - 1) == ';')
                    && Character.isWhitespace(shaderText.charAt(attributeStartLocation + ATTRIBUTE_KEYWORD.length())))) {
                attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
                continue;

            }

            int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
            int end = shaderText.indexOf(";", begin);

            String attributeLine = shaderText.substring(begin, end).trim();
            String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length()).trim();

            SetAttribLocation(attributeName, attribNumber);
            attribNumber++;

            attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
        }
    }

    private void SetAttribLocation(String attributeName, int location)
    {
        glBindAttribLocation(shaderResources.GetProgram(), location, attributeName);
    }

    private HashMap<String, ArrayList<GLSLStruct>> FindUniformStructs(String shaderText)
    {
        HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<>();

        final String STRUCT_KEYWORD = "struct";
        int structStartLocation = shaderText.indexOf(STRUCT_KEYWORD);
        while(structStartLocation != -1)
        {
            if(!(structStartLocation != 0
                    && (Character.isWhitespace(shaderText.charAt(structStartLocation - 1)) || shaderText.charAt(structStartLocation - 1) == ';')
                    && Character.isWhitespace(shaderText.charAt(structStartLocation + STRUCT_KEYWORD.length())))) {
                structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
                continue;
            }

            int nameBegin = structStartLocation + STRUCT_KEYWORD.length() + 1;
            int braceBegin = shaderText.indexOf("{", nameBegin);
            int braceEnd = shaderText.indexOf("}", braceBegin);

            String structName = shaderText.substring(nameBegin, braceBegin).trim();
            ArrayList<GLSLStruct> glslStructs = new ArrayList<GLSLStruct>();

            int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
            while(componentSemicolonPos != -1 && componentSemicolonPos < braceEnd)
            {
                int componentNameEnd = componentSemicolonPos + 1;

                while(Character.isWhitespace(shaderText.charAt(componentNameEnd - 1)) || shaderText.charAt(componentNameEnd - 1) == ';')
                    componentNameEnd--;

                int componentNameStart = componentSemicolonPos;

                while(!Character.isWhitespace(shaderText.charAt(componentNameStart - 1)))
                    componentNameStart--;

                int componentTypeEnd = componentNameStart;

                while(Character.isWhitespace(shaderText.charAt(componentTypeEnd - 1)))
                    componentTypeEnd--;

                int componentTypeStart = componentTypeEnd;

                while(!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1)))
                    componentTypeStart--;

                String componentName = shaderText.substring(componentNameStart, componentNameEnd);
                String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);

                GLSLStruct glslStruct = new GLSLStruct();
                glslStruct.name = componentName;
                glslStruct.type = componentType;

                glslStructs.add(glslStruct);

                componentSemicolonPos = shaderText.indexOf(";", componentSemicolonPos + 1);
            }

            result.put(structName, glslStructs);

            structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
        }

        return result;
    }

    private class GLSLStruct
    {
        public String name;
        public String type;
    }

    private void AddUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs)
    {
        boolean addThis = true;
        ArrayList<GLSLStruct> structComponents = structs.get(uniformType);

        if(structComponents != null)
        {
            addThis = false;
            for(GLSLStruct struct : structComponents)
            {
                AddUniform(uniformName + "." + struct.name, struct.type, structs);
            }
        }

        if(!addThis)
            return;

        int uniformLocation = glGetUniformLocation(shaderResources.GetProgram(), uniformName);

        if(uniformLocation == 0xFFFFFFFF)
        {
            System.err.println("Error: Could not find uniform: " + uniformName);
            new Exception().printStackTrace();
            System.exit(1);
        }

        shaderResources.GetUniforms().put(uniformName, uniformLocation);
    }

    public void bind()
    {
        glUseProgram(shaderResources.GetProgram());
    }

    private void AddVertexShader(String text)
    {
        AddProgram(text, GL_VERTEX_SHADER);
    }

    private void AddFragmentShader(String text)
    {
        AddProgram(text, GL_FRAGMENT_SHADER);
    }
    private void CompileShader()
    {
        glLinkProgram(shaderResources.GetProgram());

        if(glGetProgrami(shaderResources.GetProgram(), GL_LINK_STATUS) == 0)
        {
            System.err.println(glGetProgramInfoLog(shaderResources.GetProgram(), 1024));
            System.exit(1);
        }

        glValidateProgram(shaderResources.GetProgram());

        if(glGetProgrami(shaderResources.GetProgram(), GL_VALIDATE_STATUS) == 0)
        {
            System.err.println(glGetProgramInfoLog(shaderResources.GetProgram(), 1024));
            System.exit(1);
        }
    }

    private void AddProgram(String text, int type)
    {
        int shader = glCreateShader(type);

        if(shader == 0)
        {
            System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
            System.exit(1);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
        {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(shaderResources.GetProgram(), shader);
    }

    public void UpdateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
    {
        material.GetTexture("diffuse").Bind();
        Light light = renderingEngine.getLight();

        Matrix4f worldMatrix = transform.GetTransformation();
        Matrix4f viewMatric = renderingEngine.getCamera().getViewPort().Mul(transform.GetTransformation());

        SetUniform("viewMatrix" , viewMatric);
        SetUniform("transform" , worldMatrix);
        SetUniform("location", transform.GetPos());
        SetUniform("cameraPos", renderingEngine.getCamera().GetTransform().GetPos());

            SetUniformf("intencity", light.getIntensity());
            SetUniform("lightPos", light.getPos());
            SetUniform("lightColor", light.getColor());

        SetUniformf("minbrightness", Float.parseFloat(Reference.configs.getValue("MinBrightness")));
        SetUniformf("shineDamp", material.GetFloat("shineDamp"));
        SetUniformf("refelct", material.GetFloat("refelct"));
    }

    private static String LoadShader(String fileName)
    {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader;
        final String INCLUDE_DIRECTIVE = "#include";

        try
        {
            shaderReader = new BufferedReader(new FileReader("./res/shader/" + fileName));
            String line;

            while((line = shaderReader.readLine()) != null)
            {
                if(line.startsWith(INCLUDE_DIRECTIVE))
                {
                    shaderSource.append(LoadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
                }
                else
                    shaderSource.append(line).append("\n");
            }

            shaderReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }


        return shaderSource.toString();
    }

    public void SetUniform(String uniformName, Vector2f value)
    {
        glUniform2f(shaderResources.GetUniforms().get(uniformName), value.GetX(), value.GetY());
    }

    public void SetUniformi(String uniformName, int value)
    {
        glUniform1i(shaderResources.GetUniforms().get(uniformName), value);
    }

    public void SetUniformf(String uniformName, float value)
    {
        glUniform1f(shaderResources.GetUniforms().get(uniformName), value);
    }

    public void SetUniform(String uniformName, Vector3f value)
    {
        glUniform3f(shaderResources.GetUniforms().get(uniformName), value.GetX(), value.GetY(), value.GetZ());
    }

    public void SetUniform(String uniformName, Matrix4f value)
    {
        glUniformMatrix4(shaderResources.GetUniforms().get(uniformName), true, Util.CreateFlippedBuffer(value));
    }
}
