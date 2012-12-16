-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 16-12-2012 a las 02:29:57
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
  `ID` bigint(20) NOT NULL,
  `APELLIDO` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `CORREO` varchar(150) COLLATE utf8mb4_bin DEFAULT NULL,
  `NOMBRE` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `PASSWORD` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `TELEFONO` bigint(20) DEFAULT NULL,
  `USERNAME` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordencompra`
--

CREATE TABLE IF NOT EXISTS `ordencompra` (
  `ID` bigint(20) NOT NULL,
  `CODIGOENVIO` bigint(20) DEFAULT NULL,
  `ENVIADO` tinyint(1) DEFAULT '0',
  `FECHA` date DEFAULT NULL,
  `CLIENTE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ORDENCOMPRA_CLIENTE_ID` (`CLIENTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE IF NOT EXISTS `producto` (
  `ID` bigint(20) NOT NULL,
  `CODIGO` bigint(20) DEFAULT NULL,
  `DESCRIPCION` varchar(150) COLLATE utf8mb4_bin DEFAULT NULL,
  `NOMBRE` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `PATHIMAGE` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `PRECIO` double NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productoordencompra`
--

CREATE TABLE IF NOT EXISTS `productoordencompra` (
  `ID` bigint(20) NOT NULL,
  `CANTIDAD` int(11) DEFAULT NULL,
  `ORDENCOMPRA_ID` bigint(20) DEFAULT NULL,
  `PRODUCTO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PRODUCTOORDENCOMPRA_PRODUCTO_ID` (`PRODUCTO_ID`),
  KEY `FK_PRODUCTOORDENCOMPRA_ORDENCOMPRA_ID` (`ORDENCOMPRA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

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
