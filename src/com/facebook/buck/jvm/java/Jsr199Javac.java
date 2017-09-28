/*
 * Copyright 2012-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.jvm.java;

import com.facebook.buck.model.BuildTarget;
import com.facebook.buck.rules.SourcePathResolver;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import java.nio.file.Path;
import javax.tools.JavaCompiler;

/** Command used to compile java libraries with a variety of ways to handle dependencies. */
public abstract class Jsr199Javac implements Javac {

  private static final JavacVersion VERSION = JavacVersion.of("in memory");

  @Override
  public JavacVersion getVersion() {
    return VERSION;
  }

  @Override
  public String getDescription(
      ImmutableList<String> options,
      ImmutableSortedSet<Path> javaSourceFilePaths,
      Path pathToSrcsList) {
    StringBuilder builder = new StringBuilder("javac ");
    Joiner.on(" ").appendTo(builder, options);
    builder.append(" ");
    builder.append("@").append(pathToSrcsList);

    return builder.toString();
  }

  @Override
  public String getShortName() {
    return "javac";
  }

  @Override
  public ImmutableList<String> getCommandPrefix(SourcePathResolver resolver) {
    throw new UnsupportedOperationException("In memory javac may not be used externally");
  }

  @Override
  public ImmutableMap<String, String> getEnvironment(SourcePathResolver resolver) {
    throw new UnsupportedOperationException("In memory javac may not be used externally");
  }

  protected abstract JavaCompiler createCompiler(JavacExecutionContext context);

  @Override
  public Invocation newBuildInvocation(
      JavacExecutionContext context,
      BuildTarget invokingRule,
      ImmutableList<String> options,
      ImmutableList<JavacPluginJsr199Fields> pluginFields,
      ImmutableSortedSet<Path> javaSourceFilePaths,
      Path pathToSrcsList,
      Path workingDirectory,
      JavacCompilationMode compilationMode,
      boolean requiredForSourceOnlyAbi) {
    return new Jsr199JavacInvocation(
        this::createCompiler,
        context,
        invokingRule,
        options,
        pluginFields,
        javaSourceFilePaths,
        pathToSrcsList,
        compilationMode,
        requiredForSourceOnlyAbi);
  }
}
