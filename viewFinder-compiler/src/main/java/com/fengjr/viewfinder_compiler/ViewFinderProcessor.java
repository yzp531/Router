package com.fengjr.viewfinder_compiler;

import com.fengjr.viewfinder_annotation.BindView;
import com.fengjr.viewfinder_annotation.OnClick;
import com.fengjr.viewfinder_compiler.model.AnnotatedClass;
import com.fengjr.viewfinder_compiler.model.BindViewField;
import com.fengjr.viewfinder_compiler.model.OnClickMethod;
import com.google.auto.service.AutoService;

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
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by zengyong on 2018/5/29
 */
@AutoService(Processor.class)
public class ViewFinderProcessor extends AbstractProcessor {


    private Filer filer;
    private Elements elements;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        elements = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add(BindView.class.getCanonicalName());
        set.add(OnClick.class.getCanonicalName());
        return set;
    }

    private Map<String , AnnotatedClass> map = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        map.clear();
        try{
            processBindView(roundEnvironment);
            processOnClick(roundEnvironment);
        }catch (IllegalArgumentException e){
            error(e.getMessage());
            return true;
        }
        for(AnnotatedClass annotatedClass : map.values()){
            try{
                info("Generating file for %s" , annotatedClass.getFullClassName());
                annotatedClass.generateFinder().writeTo(filer);
            }catch (Exception e){
                error("Generating file failed , reason : %s" , e.getMessage());
                return true;
            }
        }
        return true;
    }

    private void processBindView(RoundEnvironment roundEnvironment){
        for(Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)){
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            BindViewField field = new BindViewField(element);
            annotatedClass.addField(field);
        }
    }

    private void processOnClick(RoundEnvironment roundEnvironment){
        for(Element element : roundEnvironment.getElementsAnnotatedWith(OnClick.class)){
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            OnClickMethod method = new OnClickMethod(element);
            annotatedClass.addMethod(method);
        }
    }

    private AnnotatedClass getAnnotatedClass(Element element){
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = classElement.getQualifiedName().toString();
        AnnotatedClass annotatedClass = map.get(fullClassName);
        if(annotatedClass == null){
            annotatedClass = new AnnotatedClass(classElement , elements);
            map.put(fullClassName , annotatedClass);
        }
        return annotatedClass;
    }

    private void error(String msg , Object... args){
        messager.printMessage(Diagnostic.Kind.ERROR , String.format(msg , args));
    }

    private void info(String msg , Object... args){
        messager.printMessage(Diagnostic.Kind.NOTE , String.format(msg , args));
    }
}
