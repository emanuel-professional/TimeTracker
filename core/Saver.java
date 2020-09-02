package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Clase encargada de guardar la informacion de un proyecto
 */
public class Saver {
    final Logger logger = LoggerFactory.getLogger(Saver.class);
    private String path; //ruta en la que se encuentra el archivo
    public Saver(final String path) {
        this.path = path;
    }

    /**
     * Metodo que guarda la informacion de un proyecto, en forma
     * de flujo de datos, en un fichero
     * @param project proyecto que queremos guardar
     */
    public void saveProject(final Project project) {
        try {
            project.stop();
            //creacion de un objeto de flujo de datos que
            //permite escribir en el fichero
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(path));
            oos.writeObject(project);
            oos.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
