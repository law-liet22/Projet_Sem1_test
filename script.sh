# Création des dossiers
mkdir -p src/out/C
mkdir -p src/out/app
mkdir -p src/out/models

# Compilation C
gcc -c src/c/gestionInventaire.c -o src/c/gestionInventaire

# Compilation Java
javac -d src/out src/app/Main.java src/models/*.java

# Pause
sleep 1

# Exécution Java
java -cp src/out app.Main

# Demande de confirmation
read -p "Supprimer les fichiers compilés ? (O/N) : " confirm

# Normalisation de la réponse
confirm=${confirm,,}   # transforme en minuscule

if [[ "$confirm" == "o" || "$confirm" == "oui" ]]; then
    echo "Suppression des fichiers compilés..."
    rm -r src/out
else
    echo "Fichiers conservés."
fi
