package main;

import dsl.SslDSL;

import java.io.File;


public class Ssl {
	public static void main(String[] args) {
		SslDSL dsl = new SslDSL();
		if(args.length > 0) {
			dsl.eval(new File(args[0]));

		} else {
			System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
		}
	}
}
