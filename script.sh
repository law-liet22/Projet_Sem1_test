# Création des dossiers
mkdir -p out/C
mkdir -p out/app
mkdir -p out/models
mkdir -p out/data

cp src/data/inventaire.csv out/data/inventaire.csv

# Compilation C
gcc src/c/trier.c -o out/C/trier

# Compilation Java
javac -d out src/app/Main.java src/models/*.java src/services/*.java src/utils/*.java

chmod +x out/C/trier
# Pause
sleep 1

# Exécution Java
java -cp out app.Main

cp out/data/inventaire.csv src/data/inventaire.csv

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
