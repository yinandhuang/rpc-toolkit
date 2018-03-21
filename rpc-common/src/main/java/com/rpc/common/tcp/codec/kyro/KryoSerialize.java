package com.rpc.common.tcp.codec.kyro;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.rpc.common.tcp.codec.RpcSerialize;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午2:00:58
 */
public class KryoSerialize implements RpcSerialize {

  private KryoPool pool = null;

  public KryoSerialize(final KryoPool pool) {
    this.pool = pool;
  }

  public void serialize(OutputStream output, Object object) throws IOException {
    Kryo kryo = pool.borrow();
    Output out = new Output(output);
    kryo.writeClassAndObject(out, object);
    out.close();
    pool.release(kryo);
  }

  public Object deserialize(InputStream input) throws IOException {
    Kryo kryo = pool.borrow();
    Input in = new Input(input);
    Object result = kryo.readClassAndObject(in);
    in.close();
    pool.release(kryo);
    return result;
  }
}
