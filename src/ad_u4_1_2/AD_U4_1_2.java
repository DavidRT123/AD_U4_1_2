/*
 * Utilizando SAX crea un programa para visualizar el contenido de un archivo XML, 
 * el nombre del archivo se introducirá por teclado, se indicará al principio si 
 * el fichero no existe, si no contiene datos (solo etiquetas especiales), para 
 * cada etiqueta con contenido de texto deberá aparecer una línea con el nombre de 
 * la etiqueta y el texto que contiene separados por :.
 */
package ad_u4_1_2;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author David Riquelme Tejero
 */
public class AD_U4_1_2 extends DefaultHandler{

    private final String RUTA = "C:\\Users\\mdfda\\Documents\\NetBeansProjects\\AD_U4_1_2\\src\\documento\\";
    private String contenido = "";
    private String nombreElemento = "sin nombre";
    
    public AD_U4_1_2(){
        super();
        String nomArchivo;
        Scanner sc = new Scanner(System.in);
        InputSource fichero;
        
        System.out.println("Introduce el nombre del fichero xml a cargar: ");
        nomArchivo = sc.nextLine();
        
        try {
            //Parser para leer el archivo xml
            XMLReader lector = XMLReaderFactory.createXMLReader();
            //Se le asigna la propia clase dado que extiende de DefaultHandler
            lector.setContentHandler(this);
            //Comprobar si existe el fichero
            File fich = new File(RUTA + nomArchivo + ".xml");
            if(!fich.exists()){
                System.err.println("El nombre del fichero proporcionado no existe. Se procede a cargar el archivo \"Jugadores.xml\" por defecto");
                fichero = new InputSource(RUTA + "Jugadores.xml");
            }else{
                //Se crea nuevo origen de datos
                fichero = new InputSource(new FileReader(RUTA + nomArchivo + ".xml"));
            }
            
            //Se lee el fichero (lo que provoca que se activen los eventos)
            lector.parse(fichero);
            //Si no hay nada en contenido mostrar mensaje
            if(contenido.isEmpty()){
                System.out.println("El archivo xml no contiene datos");
            }
        } catch (SAXException | IOException ex) {
            Logger.getLogger(AD_U4_1_2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Método main
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AD_U4_1_2();
    }

    @Override
    public void startElement(String string, String string1, String string2, Attributes atrbts) {
        nombreElemento = string1;
    }

    @Override
    public void characters(char[] chars, int i, int i1) {
        String caracter = new String(chars, i, i1);
        contenido = contenido + caracter.replaceAll("[\t\n]", "");
        //Elimino espacios y compruebo si esta vacio
        if(!caracter.trim().isEmpty()){
            imprimeNombreValor(caracter.toCharArray());
        }
    }
    /**
     * Método para, en caso de contener datos una etiqueta, mostrarlos precedidos por su nombre
     * @param chars 
     */
    private void imprimeNombreValor(char[] chars){
        System.out.println(nombreElemento + ":" + String.valueOf(chars));
    }
}
