package com.unice.dsl;


import com.unice.dsl.engine.Runner;
import com.unice.dsl.engine.SslDSL;

import java.io.File;


public class Ssl {
    public static void main(String[] args) {
        SslDSL dsl = new SslDSL();
        if (args.length > 0) {
            String[] pathDecompose = args[0].split("/");
            Runner.currentFile = pathDecompose[pathDecompose.length - 1];
            dsl.eval(new File(args[0]));
        } else {
            System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
        }
    }
}
