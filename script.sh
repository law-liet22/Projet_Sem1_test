# Création des dossiers
mkdir -p /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/C
mkdir -p /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/app
mkdir -p /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/models
mkdir -p /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/data

cp /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/src/data/inventaire.csv /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/data/inventaire.csv

# Compilation C
gcc /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/src/c/trier.c -o /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/C/trier

# Compilation Java
javac -d /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/src/app/Main.java /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/src/models/*.java /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/src/services/*.java /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/src/utils/*.java

# Ajout des droits d'exécution pour le fichier C complié 
chmod +x /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/C/trier
# Pause
# sleep 1

# Exécution Java
java -cp /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out app.Main

cp /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/data/inventaire.csv /home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/src/data/inventaire.csv

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

exit