package go.skatebogota.goskate.data.models

/**
 *  Modelo de datos que representa los parametros de configuracion de LocationManager
 *  @param interval intervalo en millisegundos de actualizacion de ubicacion
 *  @param fastestInterval intervalo en milisegundos de los mas rapido que se puede actualizar
 *  la ubicacion
 */
class LocationConfig(val interval: Int, val fastestInterval: Int) {

}