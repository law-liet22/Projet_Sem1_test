#!/bin/bash
# Script de compilation et d'exécution du projet de gestion d'inventaire
# Compile le programme C, les classes Java, puis lance l'application

# === Création de l'arborescence de dossiers pour les fichiers compilés ===
# -p : crée les dossiers parents si nécessaires, pas d'erreur si déjà existants
mkdir -p out/C
mkdir -p out/app
mkdir -p out/models
mkdir -p out/data

# === Copie du fichier CSV source vers le répertoire de sortie ===
# Permet de travailler sur une copie sans modifier l'original
cp src/data/inventaire.csv out/data/inventaire.csv

# === Compilation du programme C ===
# gcc : compilateur C
# -o : spécifie le nom du fichier de sortie
gcc src/c/trier.c -o out/C/trier

# === Compilation de tous les fichiers Java ===
# javac : compilateur Java
# -d : spécifie le répertoire de destination pour les fichiers .class
# *.java : compile tous les fichiers Java de chaque package
javac -d out src/app/Main.java src/models/*.java src/services/*.java src/utils/*.java

# === Ajout des droits d'exécution au programme C compilé ===
# chmod +x : rend le fichier exécutable
chmod +x out/C/trier

# === Exécution du programme Java ===
# java : interpréteur Java
# -cp : spécifie le classpath (chemin des classes)
# app.Main : classe principale à exécuter (package.classe)
java -cp out app.Main

# === Sauvegarde du CSV modifié vers le dossier source ===
# Copie les modifications apportées pendant l'exécution vers le fichier source
cp out/data/inventaire.csv src/data/inventaire.csv

# === Nettoyage optionnel des fichiers compilés ===
# Demande à l'utilisateur s'il souhaite supprimer les fichiers temporaires
read -p "Supprimer les fichiers compilés ? (O/N) : " confirm

# Normalisation de la réponse en minuscules
confirm=${confirm,,}

# Vérifie la réponse de l'utilisateur
if [[ "$confirm" == "o" || "$confirm" == "oui" ]]; then
    echo "Suppression des fichiers compilés..."
    sleep 1
    rm -r out  # Supprime récursivement le dossier out
    clear      # Efface l'écran du terminal
else
    echo "Fichiers conservés."
    sleep 1
    clear      # Efface l'écran du terminal
fi