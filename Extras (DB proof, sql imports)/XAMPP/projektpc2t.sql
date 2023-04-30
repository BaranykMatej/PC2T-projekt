-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Počítač: 127.0.0.1
-- Vytvořeno: Ned 30. dub 2023, 12:11
-- Verze serveru: 10.4.28-MariaDB
-- Verze PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáze: `projektpc2t`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `movies`
--

DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies` (
  `title` varchar(255) NOT NULL,
  `director` varchar(255) NOT NULL,
  `release_year` int(11) NOT NULL,
  `actors` varchar(255) NOT NULL,
  `recommended_age` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Vyprázdnit tabulku před vkládáním `movies`
--

TRUNCATE TABLE `movies`;
--
-- Vypisuji data pro tabulku `movies`
--

INSERT INTO `movies` (`title`, `director`, `release_year`, `actors`, `recommended_age`) VALUES
('Auta', 'James Who', 2001, 'Michael, Tom', 7);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
