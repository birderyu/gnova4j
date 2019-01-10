package gnova.config;

import gnova.config.annotation.Configure;
import gnova.config.annotation.Configures;
import gnova.config.handler.SimpleConfiger;
import gnova.config.annotation.Container;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class App {

    public static class Sample {

        @Configure("f0")
        private boolean ff0;
        @Configure("f1")
        private short ff1;
        @Configure("f2")
        private int ff2;
        @Configure("f3")
        private long ff3;
        @Configure("f4")
        private float ff4;
        @Configure("f5")
        private double ff5;
        @Configure("f6")
        private String ff6;
        @Configure("f7")
        private Sample ff7;
        @Configures(value = "f8", container = Container.Array, clazz = int.class)
        private int[] ff8;
        @Configures(value = "f9", container = Container.Array, clazz = String.class)
        private String[] ff9;
        @Configures(value = "f10", container = Container.Collection, clazz = Sample.class)
        private List<Sample> ff10;
        public boolean isFf0() {
            return ff0;
        }
        public void setFf0(boolean ff0) {
            this.ff0 = ff0;
        }
        public short getFf1() {
            return ff1;
        }
        public void setFf1(short ff1) {
            this.ff1 = ff1;
        }
        public int getFf2() {
            return ff2;
        }
        public void setFf2(int ff2) {
            this.ff2 = ff2;
        }
        public long getFf3() {
            return ff3;
        }
        public void setFf3(long ff3) {
            this.ff3 = ff3;
        }
        public float getFf4() {
            return ff4;
        }
        public void setFf4(float ff4) {
            this.ff4 = ff4;
        }
        public double getFf5() {
            return ff5;
        }
        public void setFf5(double ff5) {
            this.ff5 = ff5;
        }
        public String getFf6() {
            return ff6;
        }
        public void setFf6(String ff6) {
            this.ff6 = ff6;
        }
        public Sample getFf7() {
            return ff7;
        }
        public void setFf7(Sample ff7) {
            this.ff7 = ff7;
        }
        public int[] getFf8() {
            return ff8;
        }
        public void setFf8(int[] ff8) {
            this.ff8 = ff8;
        }
        public String[] getFf9() {
            return ff9;
        }
        public void setFf9(String[] ff9) {
            this.ff9 = ff9;
        }
        public List<Sample> getFf10() {
            return ff10;
        }
        public void setFf10(List<Sample> ff10) {
            this.ff10 = ff10;
        }
    }

    public static class SubSample {

    }

    public static void main(String[] args)
            throws ConfigException, IOException {

        short aaa = 129;
        byte bbb = (byte) aaa;
        int ccc = (short) bbb + 256;

        Properties p = new Properties();
        p.load(Object.class.getResourceAsStream("/sample.properties"));
        Map<String, Object> obj = new HashMap<>();
        for (Map.Entry entry : p.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            String[] keys = key.split(".");

            Object sub = obj;
            for (int i = 0; i < keys.length; i++) {
                String _key = keys[i];
                if (_key == null ||
                        (_key = _key.trim()).isEmpty()) {
                    break;
                }
            }
        }



        int stop = 1;
        stop++;

    }

    public static void yaml() throws ConfigException {
        InputStream is = Object.class.getResourceAsStream("/sample.yaml");

        Configer configer = new SimpleConfiger();
        Sample s = configer.read("sample", is, Strategy.Yaml, Sample.class);

        String ss = configer.read("sample",
                Object.class.getResourceAsStream("/sample2.yaml"),
                Strategy.Yaml, String.class);

        int stop = 1;
        stop++;
    }

    public static void json() throws ConfigException {
        InputStream is = Object.class.getResourceAsStream("/sample.json");

        Configer configer = new SimpleConfiger();
        Sample s = configer.read("sample", is, Strategy.Json, Sample.class);

        String ss = configer.read("sample",
                Object.class.getResourceAsStream("/sample2.json"),
                Strategy.Json, String.class);

        int stop = 1;
        stop++;
    }

}
