Projet - Jeu serieux
=================

Ce dépot a été créé pour que les différents membres du projet puissent travailler ensemble sur les différentes tâches.

Ce projet se déroule dans le cadre du Master 2 MATIS à l'université du Havre, il a pour but de nous faire découvrir la gestion d'un projet de son commencement jusqu'à son aboutissement et ce en travaillant en équipe. Ce projet doit nous permettre d'appliquer tout ce qui a été vu lors de cette année concernant la gestion de projet ainsi que le travail collaboratif.

Objectif du projet
=============

Le but de ce projet, pour nos clients est la création d'un jeu sérieux sur le fonctionnement du port du Havre, plus principalement sur la gestion des arrivés au port et l'assignation de ces navires arrivés vers les quais, l'objectif du jeu sera donc de cumulé le moins de retard possible sur une partie.


DocumentationProjet
=============

Ce dossier contient toute la documentation produite lors du module "Gestion de projet" et c'est donc sur celle-ci que l'on s'est basé pour produire le jeu sérieux

SimulationCapitaineriePort
==========================

Ce dossier contient tout le code produit lors de ce projet

Lancer le jeu (avec Maven)
=============

    mvn compile 
    
    mvn exec:java -Dexec.mainClass=jeuserieux.presentation.IHM
