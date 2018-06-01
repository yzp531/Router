package com.fengjr.router_compiler;

import com.fengjr.router_annotation.RouteAnnotation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by zengyong on 2018/5/31
 */
@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;
    private Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        elements = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(set != null && !set.isEmpty()){
            HashMap<String , String> nameMap = new HashMap<>();
            for(Element element : roundEnvironment.getElementsAnnotatedWith(RouteAnnotation.class)){
                RouteAnnotation routeAnnotation = element.getAnnotation(RouteAnnotation.class);
                TypeElement typeElement = (TypeElement) element;
                String clazzName = typeElement.getSimpleName().toString();
                String qualifiedPackName = elements.getPackageOf(element).getQualifiedName().toString();
                String qualifiedClazzName = qualifiedPackName + "." + clazzName;
                nameMap.put(routeAnnotation.name() ,qualifiedClazzName);
            }
            try {
                generateJavaFile(nameMap);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR , e.getMessage());
                return true;
            }
        }
        return true;
    }

    private void generateJavaFile(HashMap<String ,String> nameMap) throws IOException {
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("routeMap = new $T<>()" , HashMap.class);
        for(String key : nameMap.keySet()){
            String name = nameMap.get(key);
            constructorBuilder.addStatement("routeMap.put($S , $S)" , key , name);
        }
        MethodSpec constructorName = constructorBuilder.build();

        MethodSpec routeName = MethodSpec.methodBuilder("getActivityName")
                                .addModifiers(Modifier.PUBLIC)
                                .returns(String.class)
                                .addParameter(String.class , "routeName")
                                .beginControlFlow("if(null != routeMap && !routeMap.isEmpty())")
                                .addStatement("return routeMap.get(routeName)")
                                .endControlFlow()
                                .addStatement("return null")
                                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("AnnotationRouter$Finder")
                                .addModifiers(Modifier.PUBLIC)
                                .addSuperinterface(TypeUtil.IPROVIDER)
                                .addMethod(constructorName)
                                .addMethod(routeName)
                                .addField(
                                        FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Map.class)
                                            , ClassName.get(String.class) , ClassName.get(String.class)),"routeMap")
                                                .addModifiers(Modifier.PRIVATE).build()
                                ).build();

        JavaFile.builder("com.fengjr.router" , typeSpec).build().writeTo(filer);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add(RouteAnnotation.class.getCanonicalName());
        return set;
    }
}
