package com.kiro.sg.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface CommandInfo {

	String description();
	String usage();
	String[] aliases();
	boolean op(); // if true only ops can run this command.
}
