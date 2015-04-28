package ejerciciohilos;

//import static ejerciciohilos.EjercicioHilos.f;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alienware
 */
class Datos implements Serializable {

    private final String campo1;
    private final String campo2;

    public Datos(String campo1, String campo2) {
        this.campo1 = campo1;
        this.campo2 = campo2;
    }

    public String getCampo1() {
        return campo1;
    }

    public String getCampo2() {
        return campo2;
    }
}

/*
 * Ejemplo usando Runnable
 *
class Hilo implements Runnable {

    @Override
    public void run() {
        Datos d;
        int i = 0;
        try {
            while (true) {
                if (f.exists() && f.canRead() && (f.length() > 0)) {
                    FileInputStream iS = new FileInputStream("archivo.bin");
                    ObjectInputStream oR = new ObjectInputStream(iS);
                    System.out.println("Encontre el archivo, lo leo");
                    while (iS.available() > 0) {
                        d = (Datos) oR.readObject();
                        System.out.println(d.getCampo1() + " " + d.getCampo2());
                    }
                    oR.close();
                    f.delete();

                    System.out.println("Termino hilo");
                    break;
                }
                Thread.sleep(1000);
                System.out.println(++i);
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
}
*/

/*
 * Ejemplo usando Thread
 */
class Hilo extends Thread {

    @Override
    public void run() {
        File f = new File("archivo.bin");
        Datos d;
        int i = 0;
        try {
            while (true) {
                if (f.exists() && f.canRead() && (f.length() > 0)) {
                    FileInputStream iS = new FileInputStream(f);
                    ObjectInputStream oR = new ObjectInputStream(iS);
                    System.out.println("Encontre el archivo, lo leo");
                    while (iS.available() > 0) {
                        d = (Datos) oR.readObject();
                        System.out.println(d.getCampo1() + " " + d.getCampo2());
                    }
                    oR.close();
                    f.delete();

                    System.out.println("Termino hilo");
                    break;
                }
                Thread.sleep(1000);
                System.out.println(++i);
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
}

public class EjercicioHilos {

    static List lista = new ArrayList();

    public static void main(String[] args) {
        /*
         * Ejemplo usando clase anonima
         *
        new Thread(new Runnable() {
            @Override
            public void run() {
                Datos d;
                int i = 0;
                try {
                    while (true) {
                        if (f.exists() && f.canRead() && (f.length() > 0)) {
                            FileInputStream iS = new FileInputStream("archivo.bin");
                            ObjectInputStream oR = new ObjectInputStream(iS);
                            System.out.println("Encontre el archivo, lo leo");
                            while (iS.available() > 0) {
                                d = (Datos) oR.readObject();
                                System.out.println(d.getCampo1() + " " + d.getCampo2());
                            }
                            oR.close();
                            f.delete();


                            System.out.println("Termino hilo");
                            break;
                        }
                        Thread.sleep(1000);
                        System.out.println(++i);
                    }
                } catch (Exception ex) {
                    System.err.println(ex.toString());
                }
            }
        }).start();
        */ 

        /* 
         * Para la clase que implementa Runnable
         * Thread t = new Thread(new Hilo());
         */ 
        Hilo t = new Hilo();
        t.start();

        System.out.println("Se inicio hilo");

        lista.add(new Datos("Valor 1 campo 1", "Valor 1 campo 2"));
        lista.add(new Datos("Valor 2 campo 1", "Valor 2 campo 2"));
        lista.add(new Datos("Valor 3 campo 1", "Valor 3 campo 2"));

        System.out.println("Se lleno coleccion");

        try {
            ObjectOutputStream oW = new ObjectOutputStream(new FileOutputStream("archivo.bin"));
            for (Object c : lista) {
                oW.writeObject(c);
            }
            oW.flush();
            oW.close();
        } catch (Exception e) {
        }
        System.out.println("Se guardo en archivo");
    }
}
