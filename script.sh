# Création des dossiers
mkdir -p out/C
mkdir -p out/app
mkdir -p out/models

# Compilation C
gcc -c src/services/triDonnees.c -o out/C/triDonnees

# Compilation Java
javac -d out src/app/Main.java src/models/*.java

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
    rm -r out
else
    echo "Fichiers conservés."
fi
