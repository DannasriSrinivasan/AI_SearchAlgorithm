Name: Dannasri Srinivasan
dannasri@Dannasris-MacBook-Pro assignment1_code_dxs8730 % ls
find_route.java	h_kassel.txt	input1.txt
dannasri@Dannasris-MacBook-Pro assignment1_code_dxs8730 % javac find_route.java 
dannasri@Dannasris-MacBook-Pro assignment1_code_dxs8730 % java find_route input1.txt Bremen Kassel 
Nodes Popped: 12
Nodes Expanded: 6
Nodes Generated: 20
Distance: 297.0 km
Route:
Bremen to Hannover, 132.0
Hannover to Kassel, 165.0
dannasri@Dannasris-MacBook-Pro assignment1_code_dxs8730 % java find_route input1.txt Bremen Frankfurt
Nodes Popped: 19
Nodes Expanded: 10
Nodes Generated: 29
Distance: 455.0 km
Route:
Bremen to Dortmund, 234.0
Dortmund to Frankfurt, 221.0
dannasri@Dannasris-MacBook-Pro assignment1_code_dxs8730 % java find_route input1.txt Bremen Munich   
Nodes Popped: 41
Nodes Expanded: 16
Nodes Generated: 49
Distance: 839.0 km
Route:
Bremen to Hannover, 132.0
Hannover to Magdeburg, 148.0
Magdeburg to Leipzig, 125.0
Leipzig to Nuremberg, 263.0
Nuremberg to Munich, 171.0
dannasri@Dannasris-MacBook-Pro assignment1_code_dxs8730 % java find_route input1.txt Bremen Munich h_kassel.txt
Nodes Popped: 46
Nodes Expanded: 16
Nodes Generated: 49
Distance: 839.0 km
Route:
Bremen to Hannover, 132.0
Hannover to Magdeburg, 148.0
Magdeburg to Leipzig, 125.0
Leipzig to Nuremberg, 263.0
Nuremberg to Munich, 171.0