mkdir out 
mkdir out/C
mkdir out/app
mkdir out/models

gcc -o out/C/triDonnees -c src/services/triDonnees.c

javac -d out src/app/Main.java src/models/*.java

sleep 3s

java -cp out app.Main

read -p "Supprimer les fichiers compilés ? (O/N) : " confirm && [[&confirm = [oO] || confirm = [oO][uU][iI]]] || exit 1

if [ $confirm -eq ]

rm -r out