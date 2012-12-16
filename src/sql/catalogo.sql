-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 16-12-2012 a las 17:33:42
-- Versión del servidor: 5.5.16
-- Versión de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `catalogo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `APELLIDO` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `CORREO` varchar(150) COLLATE utf8mb4_bin DEFAULT NULL,
  `NOMBRE` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `PASSWORD` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `TELEFONO` bigint(20) DEFAULT NULL,
  `USERNAME` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`ID`, `APELLIDO`, `CORREO`, `NOMBRE`, `PASSWORD`, `TELEFONO`, `USERNAME`) VALUES
(1, 'LOPEZ', 'maurofedericolopez@yahoo.com.ar', 'MAURO FEDERICO', '123456', 446572, 'MAUROLOPEZ'),
(2, 'GEL', 'mgel@untdf.edu.ar', 'MATIAS', '123456', 435261, 'MATIASGEL'),
(3, 'SENSAN', 'kukagres@hotmail.com', 'LEANDRO', '123456', 446271, 'LEANDROSENSAN');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordencompra`
--

CREATE TABLE IF NOT EXISTS `ordencompra` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODIGOENVIO` bigint(20) DEFAULT NULL,
  `ENVIADO` tinyint(1) DEFAULT '0',
  `FECHA` date DEFAULT NULL,
  `CLIENTE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ORDENCOMPRA_CLIENTE_ID` (`CLIENTE_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `ordencompra`
--

INSERT INTO `ordencompra` (`ID`, `CODIGOENVIO`, `ENVIADO`, `FECHA`, `CLIENTE_ID`) VALUES
(1, NULL, 0, '2012-12-10', 1),
(2, 10203040, 1, '2012-12-05', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE IF NOT EXISTS `producto` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODIGO` bigint(20) NOT NULL,
  `DESCRIPCION` varchar(150) CHARACTER SET utf8 COLLATE utf8_spanish_ci DEFAULT NULL,
  `NOMBRE` varchar(45) CHARACTER SET utf8 COLLATE utf8_spanish_ci DEFAULT NULL,
  `PATHIMAGE` varchar(255) CHARACTER SET utf8 COLLATE utf8_spanish_ci DEFAULT NULL,
  `PRECIO` double NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODIGO` (`CODIGO`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin AUTO_INCREMENT=11 ;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`ID`, `CODIGO`, `DESCRIPCION`, `NOMBRE`, `PATHIMAGE`, `PRECIO`) VALUES
(1, 100000, 'PLATO HONDO COLOR 22 CM VAJILLA CERAMICA GASTRONOMICA', NULL, 'platohondocolor22cmvajillaceramicagastronomica', 25),
(2, 100001, 'TAZA CERAMICA', NULL, 'tazaceramica', 21),
(3, 100002, 'TERMO + MATE + LATA DECORADOS EN VINILO', NULL, 'termomatelatadecoradosenvinilo', 250),
(4, 100003, 'SET 3 HERMETICOS TUPPERWARE', NULL, 'set3hermeticostupperware', 218),
(5, 100004, 'OLLA DE ACERO QUIRURGICO + CESTO SPAGHETIERO', NULL, 'olladeaceroquirurgicocestospaghetiero', 1404),
(6, 100005, 'VASO TRAGO LARGO DE CRISTAL PACK X6', NULL, 'vasotragolargodecristalpackx6', 180),
(7, 100006, 'CAZUELA DE BARRO CON ESMALTE', NULL, 'cazueladebarroconesmalte', 12),
(8, 100007, '12 COPAS VIDRIO', NULL, '12copasvidrio', 114),
(9, 100008, '24 CUBIERTOS TRAMONTINA', NULL, '24cubiertostramontina', 399),
(10, 100009, 'POCHOCLERA FAMILIAR OLLA ALUMINIO P/ POP CORN SUPER GRANDE', NULL, 'pochoclerafamiliarollaaluminio', 169);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productoordencompra`
--

CREATE TABLE IF NOT EXISTS `productoordencompra` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CANTIDAD` int(11) DEFAULT NULL,
  `ORDENCOMPRA_ID` bigint(20) DEFAULT NULL,
  `PRODUCTO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PRODUCTOORDENCOMPRA_PRODUCTO_ID` (`PRODUCTO_ID`),
  KEY `FK_PRODUCTOORDENCOMPRA_ORDENCOMPRA_ID` (`ORDENCOMPRA_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `productoordencompra`
--

INSERT INTO `productoordencompra` (`ID`, `CANTIDAD`, `ORDENCOMPRA_ID`, `PRODUCTO_ID`) VALUES
(1, 1, 2, 1),
(2, 2, 2, 7),
(3, 1, 1, 10),
(4, 2, 1, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sequence`
--

CREATE TABLE IF NOT EXISTS `sequence` (
  `SEQ_NAME` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Volcado de datos para la tabla `sequence`
--

INSERT INTO `sequence` (`SEQ_NAME`, `SEQ_COUNT`) VALUES
('SEQ_GEN', 0);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ordencompra`
--
ALTER TABLE `ordencompra`
  ADD CONSTRAINT `FK_ORDENCOMPRA_CLIENTE_ID` FOREIGN KEY (`CLIENTE_ID`) REFERENCES `cliente` (`ID`);

--
-- Filtros para la tabla `productoordencompra`
--
ALTER TABLE `productoordencompra`
  ADD CONSTRAINT `FK_PRODUCTOORDENCOMPRA_ORDENCOMPRA_ID` FOREIGN KEY (`ORDENCOMPRA_ID`) REFERENCES `ordencompra` (`ID`),
  ADD CONSTRAINT `FK_PRODUCTOORDENCOMPRA_PRODUCTO_ID` FOREIGN KEY (`PRODUCTO_ID`) REFERENCES `producto` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
