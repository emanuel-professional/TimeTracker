package core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Clase encargada de cargar la informacion de un proyecto
 */
public class Loader {
    final Logger logger = LoggerFactory.getLogger(Loader.class);
    private String path; //ruta en la que se encuentra el archivo
    public Loader(final String path) {
        this.path = path;
    }

    /**
     * Metodo que carga la informacion de un proyecto
     * de un fichero y la guarda en un proyecto
     * @return proyecto cargado
     */
    public Project loadProject() {
        Project project = null;
        try {
            //creacion de un objeto de flujo de datos
            // que permite leer del fichero
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(path));
            project = (Project) ois.readObject();
            ois.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return project;

    }
}
