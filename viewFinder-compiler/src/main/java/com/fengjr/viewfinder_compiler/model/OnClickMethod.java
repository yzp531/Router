package com.fengjr.viewfinder_compiler.model;

import com.fengjr.viewfinder_annotation.OnClick;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;

/**
 * Created by zengyong on 2018/5/30
 */

public class OnClickMethod {

    private ExecutableElement methodElement;
    private Name methodName;
    public int[] ids;

    public OnClickMethod(Element element){
        if(element.getKind() != ElementKind.METHOD){
            throw new IllegalArgumentException(
              String.format("Only methods can be annotated with @%s" , OnClick.class.getSimpleName())
            );
        }
        this.methodElement = (ExecutableElement) element;
        this.ids = methodElement.getAnnotation(OnClick.class).value();

        if(ids == null){
            throw new IllegalArgumentException(
                    String.format("Must set valid ids for @%s" , OnClick.class.getSimpleName())
            );
        }else{
            for(int id : ids){
                if(id < 0){
                    throw new IllegalArgumentException(String.format("Must set valid id for @%s" , OnClick.class.getSimpleName()));
                }
            }
        }
        this.methodName = methodElement.getSimpleName();
        List<? extends VariableElement> paraments = methodElement.getParameters();
        if(paraments.size() > 0){
            throw new IllegalArgumentException(
                    String.format("The method annotated with @%s must have no paramsters" , OnClick.class.getSimpleName())
            );
        }
    }

    public Name getMethodName(){
        return methodName;
    }
}
