package mx.educarancho.logica.dominio;

public class PreguntaDificil {
    private int numero;
    private String pregunta;
    private String respuestNumerica;
    private String respuestaUnidad;
    private String tema;
    private String archivoAyuda;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuestNumerica() {
        return respuestNumerica;
    }

    public void setRespuestNumerica(String respuestNumerica) {
        this.respuestNumerica = respuestNumerica;
    }

    public String getRespuestaUnidad() {
        return respuestaUnidad;
    }

    public void setRespuestaUnidad(String respuestaUnidad) {
        this.respuestaUnidad = respuestaUnidad;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getArchivoAyuda() {
        return archivoAyuda;
    }

    public void setArchivoAyuda(String archivoAyuda) {
        this.archivoAyuda = archivoAyuda;
    }
    
}
