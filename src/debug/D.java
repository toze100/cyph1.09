/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package debug;

/**
 *
 * @author tony
 */
public class D {
    /*
    Esta variavel controla a execução da instrução de impressão na consola para debug
    em qualquer class, basta importar o seguinte:
    import pt.ipl.estg.eipl.esoft1.pl2.pl.g4.debugger.D;
    
    e utilizar em qualquer lugar como
     D.deb("a sair do programa");
    */
    private static final boolean debug= false;   //true ou false
    /*
    public D (){// empty constructor
    }
    */
    public static void deb(String sts){
        if (debug) {            
            System.out.println(sts);
        }        
    }
    public static void deb (int sts){
        if (debug){
            System.out.println(Integer.toString(sts));
        }
        
    }
}
