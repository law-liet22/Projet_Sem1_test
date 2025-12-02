# Création des dossiers
mkdir -p out/C
mkdir -p out/app
mkdir -p out/models

# Compilation C
gcc src/c/gestionInventaire.c -o out/C/gestionInventaire

# Compilation Java
javac -d out src/app/Main.java src/models/*.java

chmod +x out/C/gestionInventaire
# Pause
sleep 1

# Exécution Java
java -cp out app.Main

# Demande de confirmation
read -p "Supprimer les fichiers compilés ? (O/N) : " confirm

# Normalisation de la réponse
confirm=${confirm,,}   # transforme en minuscule

if [[ "$confirm" == "o" || "$confirm" == "oui" ]]; then
    echo "Suppression des fichiers compilés..."
    sleep 1
    rm -r out
    clear
else
    echo "Fichiers conservés."
    seelp 1
    clear
fi
