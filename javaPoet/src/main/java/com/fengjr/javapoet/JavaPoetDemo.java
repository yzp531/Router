package com.fengjr.javapoet;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;

/**
 * Created by zengyong on 2018/5/30
 */

public class JavaPoetDemo {



    public static void main(String[] args) throws Exception {

        TypeSpec clazz = TypeSpec.classBuilder("Clazz")
                        .addTypeVariable(TypeVariableName.get("T"))
                        .addModifiers(Modifier.PUBLIC , Modifier.FINAL)
                        .superclass(String.class)
                        .addSuperinterface(Serializable.class)
                        .addSuperinterface(ParameterizedTypeName.get(Comparable.class , String.class))
                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Comparable.class), WildcardTypeName.subtypeOf(String.class)))
                        .addField(
                                FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Map.class) ,
                                        ClassName.get(String.class) , TypeVariableName.get("T"))    , "mfoo")
                                        .addModifiers(Modifier.PRIVATE , Modifier.FINAL , Modifier.STATIC)
                                        .initializer("new $T()" , HashMap.class)
                                        .build()
                        )
                        .addStaticBlock(
                                CodeBlock.builder()
                                        .addStatement("System.out.println($S)" , "static code")
                                        .build()
                        )
                        .addInitializerBlock(
                                CodeBlock.builder()
                                        .addStatement("System.out.println($S)" , "block code")
                                        .build()
                        )
                        .addMethod(
                                MethodSpec.methodBuilder("method")
                                            .addModifiers(Modifier.PUBLIC)
                                            .addAnnotation(Override.class)
                                            .returns(int.class)
                                            .addTypeVariable(TypeVariableName.get("T"))
                                            .addParameter(String.class , "str")
                                            .addParameter(TypeVariableName.get("T") , "t")
                                            .addParameter(
                                                    ParameterizedTypeName.get(ClassName.get(Map.class) ,
                                                            ClassName.get(Integer.class) ,
                                                            WildcardTypeName.subtypeOf(TypeVariableName.get("T"))) , "map"
                                            )
                                            .addException(IOException.class)
                                            .addException(RuntimeException.class)
                                            .addStatement("$T bar = $S" , String.class , "a string")
                                            .addCode(
                                                    CodeBlock.builder()
                                                            .addStatement("int foo = 1;")
                                                            .addStatement(
                                                                    "Object obj = $T" ,
                                                                    ParameterizedTypeName.get(ClassName.get(HashMap.class) ,
                                                                            ClassName.get(Integer.class) , WildcardTypeName.subtypeOf(TypeVariableName.get("T"))))
                                                            .addStatement(
                                                                    "new $T($L).start()" , Thread.class ,
                                                                    TypeSpec.anonymousClassBuilder("$T param" , String.class)
                                                                    .addSuperinterface(Runnable.class)
                                                                    .addMethod(
                                                                            MethodSpec.methodBuilder("run")
                                                                                .addAnnotation(Override.class)
                                                                                .returns(void.class)
                                                                                .build()
                                                                    )
                                                                    .build()
                                                            )
                                                            .build()
                                            )
                                            .beginControlFlow("for(int i=0;i<5;i++)")
                                            .addStatement("System.out.println(i)")
                                            .endControlFlow()
                                            .beginControlFlow("while(false)")
                                            .addStatement("System.out.println($S)" , "while")
                                            .endControlFlow()
                                            .beginControlFlow("do")
                                            .addStatement("System.out.println($S)" , "do while")
                                            .endControlFlow("while(false)")
                                            .beginControlFlow("if(false)")
                                            .addStatement("System.out.println($S)" , "if")
                                            .nextControlFlow("else if(false)")
                                            .addStatement("System.out.println($S)" , "else if")
                                            .endControlFlow()
                                            .beginControlFlow("try")
                                            .nextControlFlow("catch($T e)" , IOException.class)
                                            .addStatement("e.printStatcktrace()")
                                            .nextControlFlow("finally")
                                            .endControlFlow()
                                            .addStatement("return 0")
                                            .build()
                        )
                        .build();


        JavaFile javaFile = JavaFile.builder("com.fengjr.javapoet" , clazz).build();

        File file = new File("Clazz.java");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(javaFile.toString().getBytes());
        fos.close();
        System.out.println(file.getAbsolutePath());
        System.out.println(javaFile.toString());
    }


}
