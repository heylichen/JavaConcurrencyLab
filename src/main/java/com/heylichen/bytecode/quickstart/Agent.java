package com.heylichen.bytecode.quickstart;

import java.lang.instrument.Instrumentation;

/**
 * Created by lc on 2016/7/12.
 */
public class Agent {
  public static void premain(String agentArgs, Instrumentation inst) {

    // registers the transformer
    inst.addTransformer(new SleepingClassFileTransformer());
  }
}
