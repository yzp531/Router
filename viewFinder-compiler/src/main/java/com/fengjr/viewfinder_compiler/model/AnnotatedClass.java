package com.fengjr.viewfinder_compiler.model;

import com.fengjr.viewfinder_compiler.TypeUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by zengyong on 2018/5/30
 */
public class AnnotatedClass {

    public TypeElement classElement;
    public List<BindViewField> mFields;
    public List<OnClickMethod> methods;
    public Elements elementUtils;

    public AnnotatedClass(TypeElement classElement , Elements elementUtils){
        this.classElement = classElement;
        this.methods = new ArrayList<>();
        this.mFields = new ArrayList<>();
        this.elementUtils = elementUtils;
    }

    public String getFullClassName(){
        return classElement.getQualifiedName().toString();
    }

    public void addMethod(OnClickMethod method){
        this.methods.add(method);
    }

    public void addField(BindViewField field){
        this.mFields.add(field);
    }

    public JavaFile generateFinder(){
        MethodSpec.Builder injectMethodBuilder =  MethodSpec.methodBuilder("inject")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addParameter(TypeName.get(classElement.asType()) , "host" , Modifier.FINAL)
                    .addParameter(TypeName.OBJECT , "source")
                    .addParameter(TypeUtil.PROVIDER , "provider");

        for(BindViewField field : mFields){
            injectMethodBuilder.addStatement("host.$N = ($T)(provider.findView(source,$L))" , field.getFieldName() ,
                    ClassName.get(field.getFieldType()) , field.getResId());
        }
        if(methods.size() > 0){
            injectMethodBuilder.addStatement("$T listener" , TypeUtil.ANDROID_ON_CLICK_LISTENER);
        }
        for(OnClickMethod clickMethod : methods){
            TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(TypeUtil.ANDROID_ON_CLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(TypeName.VOID)
                        .addParameter(TypeUtil.ANDROID_VIEW , "view")
                        .addStatement("host.$N()" , clickMethod.getMethodName())
                        .build())
                    .build();
             injectMethodBuilder.addStatement("listener = $L" , listener);
             for(int id : clickMethod.ids){
                 injectMethodBuilder.addStatement("provider.findView(source , $L).setOnClickListener(listener)" , id);
             }

        }
        TypeSpec findClass = TypeSpec.classBuilder(classElement.getSimpleName() + "$$Finder")
                                .addModifiers(Modifier.PUBLIC)
                                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.FINDER , TypeName.get(classElement.asType())))
                                .addMethod(injectMethodBuilder.build())
                                .build();

        String packageName = elementUtils.getPackageOf(classElement).getQualifiedName().toString();
        return JavaFile.builder(packageName , findClass).build();
    }

}
