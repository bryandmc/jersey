/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jersey.tests.performance.benchmark;

import java.util.concurrent.TimeUnit;

import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * {@link JerseyUriBuilder} benchmark for parsing templates.
 *
 * @author David Schlosnagle
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 16, time = 2500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 16, time = 2500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class JerseyUriBuilderBenchmark {

    @Param(value = {"http://localhost:8080/a/b/c", "https://localhost:443/{a}/{b}/{c:.+}"})
    private String uriTemplate;

    private volatile JerseyUriBuilder uriBuilder;

    @Setup
    public void start() throws Exception {
        uriBuilder = new JerseyUriBuilder();
    }

    @Benchmark
    public JerseyUriBuilder uri() throws Exception {
        return uriBuilder.uri(uriTemplate);
    }

    public static void main(final String[] args) throws Exception {
        final Options opt = new OptionsBuilder()
                // Register our benchmarks.
                .include(JerseyUriBuilderBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
