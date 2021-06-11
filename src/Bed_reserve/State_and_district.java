package Bed_reserve;

import java.util.Scanner;

public class State_and_district {
        private static String[][] india = {
                        { "Andaman and Nicobar Islands", "Nicobars", "North And Middle Andaman", "South Andaman" },
                        { "AndhraPradesh", "Anantapur", "Chittoor", "East Godavari", "Guntur", "Kadapa", "Krishna",
                                        "Kurnool", "Prakasam", "Sri Potti Sri Ramulu Nellore", "Srikakulam",
                                        "Visakhapatnam", "Vizianagaram", "West Godavari" },
                        { "Arunachal Pradesh", "Anjaw", "Changlang", "Dibang Valley", "East Kameng", "East Siang",
                                        "Kamle", "Kra Daadi", "Kurung Kumey", "Lepa-Rada", "Lohit", "Longding",
                                        "Lower Dibang Valley", "Lower Siang", "Lower Subansiri", "Namsai",
                                        "Pakke-Kessang", "Papum Pare", "Shi-Yomi", "Siang", "Tawang", "Tirap",
                                        "Upper Siang", "Upper Subansiri", "West Kameng", "West Siang" },
                        { "Assam", "Barpeta", "Baska", "Biswanath", "Bongaigaon", "Cachar", "Charaideo", "Chirang",
                                        "Darrang", "Dhemaji", "Dhubri", "Dibrugarh", "Dima Hasao", "Goalpara",
                                        "Golaghat", "Hailakandi", "Hojai", "Jorhat", "Kamrup", "Kamrup Metropolitan",
                                        "Karbi Anglong", "Karimganj", "Kokrajhar", "Lakhimpur", "Majuli", "Morigaon",
                                        "Nagaon", "Nalbari", "Sivasagar", "Sonitpur", "South Salmara Mankachar",
                                        "Tinsukia", "Udalguri", "West Karbi Anglong" },
                        { "Bihar", "Araria", "Arwal", "Aurangabad", "Banka", "Begusarai", "Bhagalpur", "Bhojpur",
                                        "Buxar", "Darbhanga", "East Champaran", "Gaya", "Gopalganj", "Jamui",
                                        "Jehanabad", "Kaimur", "Katihar", "Khagaria", "Kishanganj", "Lakhisarai",
                                        "Madhepura", "Madhubani", "Munger", "Muzaffarpur", "Nalanda", "Nawada", "Patna",
                                        "Purnia", "Rohtas", "Saharsa", "Samastipur", "Saran", "Sheikhpura", "Sheohar",
                                        "Sitamarhi", "Siwan", "Supaul", "Vaishali", "West Champaran" },
                        { "Chandigarh", "Chandigarh" },
                        { "Chhattisgarh", "Balod", "Baloda Bazar", "Balrampur", "Bastar", "Bemetara", "Bijapur",
                                        "Bilaspur", "Dantewada", "Dhamtari", "Durg", "Gariaband", "Janjgir-Champa",
                                        "Jashpur", "Kanker", "Kawardha", "Kondagaon", "Korba", "Korea", "Mahasamund",
                                        "Mungeli", "Narayanpur", "Raigarh", "Raipur", "Rajnandgaon", "Sukma",
                                        "Surajpur", "Surguja" },
                        { "Dadra and Nagar Haveli and Daman and Diu", "Dadra and Nagar Haveli", "Daman", "Diu" },
                        { "Delhi", "Central Delhi", "East Delhi", "New Delhi", "North Delhi", "North East Delhi",
                                        "North West Delhi", "Shahdara", "South Delhi", "South Eest Delhi",
                                        "South West Delhi", "West Delhi" },
                        { "Goa", "North Goa", "South Goa" },
                        { "Gujarat", "Ahmedabad", "Amreli", "Anand", "Aravalli", "Banaskantha", "Bharuch", "Bhavnagar",
                                        "Botad", "Chhota Udaipur", "Dahod", "Dang", "Devbhoomi Dwarka", "Gandhinagar",
                                        "Gir Somnath", "Jamnagar", "Junagadh", "Kheda", "Kutch", "Mahisagar", "Mehsana",
                                        "Morbi", "Narmada", "Navsari", "Panchmahal", "Patan", "Porbandar", "Rajkot",
                                        "Sabarkantha", "Surat", "Surendranagar", "Tapi", "Vadodara", "Valsad" },
                        { "Haryana", "Ambala", "Bhiwani", "Charkhi Dadri", "Faridabad", "Fatehabad", "Gurugram",
                                        "Hisar", "Jhajjar", "Jind", "Kaithal", "Karnal", "Kurukshetra", "Mahendragarh",
                                        "Nuh", "Palwal", "Panchkula", "Panipat", "Rewari", "Rohtak", "Sirsa", "Sonipat",
                                        "Yamunanagar" },
                        { "Himachal Pradesh", "Bilaspur", "Chamba", "Hamirpur", "Kangra", "Kinnaur", "Kullu",
                                        "Lahaul and Spiti", "Mandi", "Shimla", "Sirmaur", "Solan", "Una" },
                        { "Jammu and Kashmir", "Anantnag", "Bandipora", "Baramulla", "Budgam", "Doda", "Ganderbal",
                                        "Jammu", "Kathua", "Kishtwar", "Kulgam", "Kupwara", "Poonch", "Pulwama",
                                        "Rajouri", "Ramban", "Reasi", "Samba", "Shopian", "Srinagar", "Udhampur" },
                        { "Jharkhand", "Bokaro", "Chatra", "Deoghar", "Dhanbad", "Dumka", "East Singhbhum", "Garhwa",
                                        "Giridih", "Godda", "Gumla", "Hazaribagh", "Jamtara", "Khunti", "Koderma",
                                        "Latehar", "Lohardaga", "Pakur", "Palamu", "Ramgarh", "Ranchi", "Sahebganj",
                                        "Seraikela", "Simdega", "West Singhbhum" },
                        { "Karnataka", "Bagalkot", "Ballari", "Belagavi", "Bengaluru Rural", "Bengaluru Urban", "Bidar",
                                        "Chamarajanagar", "Chikballapur", "Chikkamagaluru", "Chitradurga",
                                        "Dakshina Kannada", "Davanagere", "Dharwad", "Gadag", "Hassan", "Haveri",
                                        "Kalaburagi", "Kodagu", "Kolar", "Koppal", "Mandya", "Mysuru", "Raichur",
                                        "Ramanagara", "Shivamogga", "Tumakuru", "Udupi", "Uttara Kannada", "Vijayapur",
                                        "Yadgir" },
                        { "Kerala", "Alappuzha", "Ernakulam", "Idukki", "Kannur", "Kasaragod", "Kollam", "Kottayam",
                                        "Kozhikode", "Malappuram", "Palakkad", "Pathanamthitta", "Thiruvananthapuram",
                                        "Thrissur", "Wayanad" },
                        { "Ladakh", "Kargil District", "Leh District" }, { "Lakshadweep", "Lakshadweep" },
                        { "Madhya Pradesh", "Agar Malwa", "Alirajpur", "Anuppur", "Ashoknagar", "Balaghat", "Barwan",
                                        "Betul", "Bhind", "Bhopal", "Burhanpur", "Chhatarpur", "Chhindwara", "Damoh",
                                        "Datia", "Dewas", "Dhar", "Dindori", "East Nimar", "Guna", "Gwalior", "Harda",
                                        "Hoshangabad", "Indore", "Jabalpur", "Jhabua", "Katni", "Mandla", "Mandsaur",
                                        "Morena", "Narsinghpur", "Neemuch", "Niwari", "Panna", "Raisen", "Rajgarh",
                                        "Ratlam", "Rewa", "Sagar", "Satna", "Sehore", "Seoni", "Shahdol", "Shajapur",
                                        "Sheopur", "Shivpuri", "Sidhi", "Singrauli", "Tikamgarh", "Ujjain", "Umaria",
                                        "Vidisha", "West Nimar" },
                        { "Maharashtra", "Ahmednagar", "Akola", "Amravati", "Aurangabad", "Beed", "Bhandara",
                                        "Buldhana", "Chandrapur", "Dhule", "Gadchiroli", "Gondia", "Hingoli", "Jalgaon",
                                        "Jalna", "Kolhapur", "Latur", "Mumbai City", "Mumbai Suburban", "Nagpur",
                                        "Nanded", "Nandurbar", "Nashik", "Osmanabad", "Palghar", "Parbhani", "Pune",
                                        "Raigad", "Ratnagiri", "Sangli", "Satara", "Sindhudurg", "Solapur", "Thane",
                                        "Wardha", "Washim", "Yavatmal" },
                        { "Manipur", "Bishnupur", "Chandel", "Churachandpur", "Imphal East", "Imphal West", "Senapati",
                                        "Tamenglong", "Thoubal", "Ukhrul" },
                        { "Meghalaya", "East Garo Hills", "East Khasi Hills", "Jaintia Hills", "Ri Bhoi",
                                        "South Garo Hills", "West Garo Hills", "West Khasi Hills" },
                        { "Mizoram", "Aizawl", "Champhai", "Kolasib", "Lawngtlai", "Lunglei", "Mamit", "Saiha",
                                        "Serchhip" },
                        { "Nagaland", "Dimapur", "Kiphire", "Kohima", "Longleng", "Mokokchung", "Mon", "Noklak",
                                        "Peren", "Phek", "Tuensang", "Wokha", "Zunheboto" },
                        { "Odisha", "Angul", "Balangir", "Balasore", "Bargarh", "Bhadrak", "Boudh", "Cuttack",
                                        "Deogarh", "Dhenkanal", "Gajapati", "Ganjam", "Jagatsinghapur", "Jajpur",
                                        "Jharsuguda", "Kalahandi", "Kandhamal", "Kendrapara", "Keonjhar", "Khordha",
                                        "Koraput", "Malkangiri", "Mayurbhanj", "Nabarangpur", "Nayagarh", "Nuapada",
                                        "Puri", "Rayagada", "Sambalpur", "Subarnapur", "Sundargarh" },
                        { "Puducherry", "Karaikal", "Mahe", "Pondicherry", "Yanam" },
                        { "Punjab", "Amritsar", "Barnala", "Bathinda", "Faridkot", "Fatehgarh Sahib", "Fazilka",
                                        "Firozpur", "Gurdaspur", "Hoshiarpur", "Jalandhar", "Kapurthala", "Ludhiana",
                                        "Mansa", "Moga", "Pathankot", "Patiala", "Rupnagar",
                                        "Sahibzada Ajit Singh Nagar", "Sangrur", "Shahid Bhagat Singh Nagar",
                                        "Sri Muktsar Sahib", "Taran Taran" },
                        { "Rajasthan", "Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bharatpur", "Bhilwara",
                                        "Bikaner", "Bundi", "Chittorgarh", "Churu", "Dausa", "Dholpur", "Dungarpur",
                                        "Hanumangarh", "Jaipur", "Jaisalmer", "Jalor", "Jhalawar", "Jhunjhunu",
                                        "Jodhpur", "Karauli", "Kota", "Nagaur", "Pali", "Pratapgarh", "Rajsamand",
                                        "Sawai Madhopur", "Sikar", "Sirohi", "Sri Ganganagar", "Tonk", "Udaipur" },
                        { "Sikkim", "East Sikkim", "North Sikkim", "South Sikkim", "West Sikkim" },
                        { "Tamil Nadu", "Ariyalur", "Chengalpattu", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri",
                                        "Dindigul", "Erode", "Kallakurichi", "Kanchipuram", "Kanyakumari", "Karur",
                                        "Krishnagiri", "Madurai", "Mayiladuthurai", "Nagapattinam", "Namakkal",
                                        "Nilgiris", "Perambalur", "Pudukkottai", "Ramanathapuram", "Ranipet", "Salem",
                                        "Sivagangai", "Tenkasi", "Thanjavur", "Theni", "Thoothukudi", "Tiruchirappalli",
                                        "Tirunelveli", "Tirupathur", "Tiruppur", "Tiruvallur", "Tiruvannamalai",
                                        "Tiruvarur", "Vellore", "Viluppuram", "Virudhunagar" },
                        { "Telangana", "Adilabad", "Bhadradri Kothagudem", "Hyderabad", "Jagitial", "Jangaon",
                                        "Jayashankar Bhupalapally", "Jogulamba Gadwal", "Kamareddy", "Karimnagar",
                                        "Khammam", "Kumarambheem Asifabad", "Mahabubabad", "Mahabubnagar",
                                        "Mancherial district", "Medak", "Medchal-Malkajgiri", "Mulugu", "Nagarkurnool",
                                        "Nalgonda", "Narayanpet", "Nirmal", "Nizamabad", "Peddapalli",
                                        "Rajanna Sircilla", "Ranga Reddy", "Sangareddy", "Siddipet", "Suryapet",
                                        "Vikarabad", "Wanaparthy", "Warangal Rural", "Warangal Urban",
                                        "Yadadri Bhuvanagiri" },
                        { "Tripura", "Dhalai", "North Tripura", "South Tripura", "West Tripura" },
                        { "Uttar Pradesh", "Agra", "Aligarh", "Allahabad", "Ambedkar Nagar", "Amethi", "Amroha",
                                        "Auraiya", "Azamgarh", "Badaun", "Bagpat", "Bahraich", "Ballia", "Balrampur",
                                        "Banda District", "Barabanki", "Bareilly", "Basti", "Bijnor", "Bulandshahr",
                                        "Chandauli(Varanasi Dehat)", "Chitrakoot", "Deoria", "Etah", "Etawah",
                                        "Faizabad", "Farrukhabad", "Fatehpur", "Firozabad", "Gautam Buddha Nagar",
                                        "Ghaziabad", "Ghazipur", "Gonda", "Gorakhpur", "Hamirpur", "Hapur District",
                                        "Hardoi", "Hathras", "Jaunpur District", "Jhansi", "Kannauj", "Kanpur Dehat",
                                        "Kanpur Nagar", "Kasganj", "Kaushambi", "Kushinagar", "Lakhimpur Kheri",
                                        "Lalitpur", "Lucknow", "Maharajganj", "Mahoba", "Mainpuri", "Mathura", "Mau",
                                        "Meerut", "Mirzapur", "Moradabad", "Muzaffarnagar", "Pilibhit", "Pratapgarh",
                                        "PrayagRaj", "Rae Bareli", "Rampur", "Saharanpur", "Sambhal",
                                        "Sant Kabir Nagar", "Sant Ravidas Nagar", "Shahjahanpur", "Shamli", "Shravasti",
                                        "Siddharthnagar", "Sitapur", "Sonbhadra", "Sultanpur", "Unnao",
                                        "Varanasi (Kashi)" },
                        { "Uttarakhand", "Almora", "Bageshwar", "Chamoli", "Champawat", "Dehradun", "Haridwar",
                                        "Nainital", "Pauri Garhwal", "Pithoragarh", "Rudraprayag", "Tehri Garhwal",
                                        "Udham Singh Nagar", "Uttarkashi" },
                        { "West Bengal", "Alipurduar", "Bankura", "Birbhum", "Cooch Behar", "Dakshin Dinajpur",
                                        "Darjeeling", "Hooghly", "Howrah", "Jalpaiguri", "Jhargram", "Kalimpong",
                                        "Kolkata", "Malda", "Murshidabad", "Nadia", "North 24 Parganas",
                                        "Paschim Bardhaman", "Paschim Medinipur", "Purba Bardhaman", "Purba Medinipur",
                                        "Purulia", "South 24 Parganas", "Uttar Dinajpur" } };

        public static String[] get_district() {
                // select state and district...
                Scanner sc = new Scanner(System.in);
                int state;
                String state_name;
                String district;
                System.out.println("\n\tplease select the State    :\n");
                int state_count = 1;
                for (int i = 0; i < india.length; i++, state_count++) {
                        System.out.println("\t\t" + state_count + "." + india[i][0]);
                }
                System.out.print("\n\tEnter the State No : ");
                state = sc.nextInt();
                state_name = india[state - 1][0];
                System.out.println("\n\tplease select the District :");
                int district_count = 1;
                for (int i = 1; i < india[state - 1].length; i++, district_count++) {
                        System.out.println("\t\t" + district_count + "." + india[state - 1][i]);
                }
                System.out.print("\nEnter the District NO : ");
                district = india[state - 1][sc.nextInt()];
                String[] state_and_district = { state_name, district };
                return state_and_district;
        }

}
