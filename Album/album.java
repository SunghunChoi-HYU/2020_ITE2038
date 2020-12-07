import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.*;

public class Hello {

	public static void main(String[] args) {
		Connection con;
		PreparedStatement pstmt;
		ResultSet rs;
		int choice0 = -1, choice1 = -1;
		int Real_Security_Num = 1112;
		Scanner scan = new Scanner(System.in);
		try {
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
			String url = "jdbc:mariadb://localhost:3306/album";
			String user = "root";
			String psw = "";
			con = DriverManager.getConnection(url, user, psw);
			while(true) {
				System.out.println("0. Exit");
				System.out.println("1. Manager Menu");
				System.out.println("2. User Menu");
				System.out.println("3. Singer Menu");
				choice0 = scan.nextInt();
				if(choice0 == 1) {
					while(true) { // MANAGER MENU
						System.out.println("------------------------------------------");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Login");
						System.out.println("2. Register");
						int choice2 = scan.nextInt();
						if(choice2 == 0) {
							break;
						}
						else if(choice2 == 1) { // LOGIN_MANAGER
							System.out.println("------------------------------------------");
							System.out.print("ID : ");
							String ID = scan.next();
							System.out.print("PW : ");
							String PW = scan.next();
							System.out.print("Secrity # from Company : ");
							int Security_Num = scan.nextInt();
							if(Security_Num == Real_Security_Num) {
								String sql = "SELECT * FROM MANAGER WHERE MANAGER_ID = ? AND MANAGER_PW = ? ";
								pstmt = con.prepareStatement(sql);
								pstmt.setString(1, ID);
								pstmt.setString(2, PW);
								rs = pstmt.executeQuery();
								if(!rs.next()) {
									System.out.println("You typed wrong ID or PW.");
								}
								else {
									
									while(true) {
										System.out.println("------------------------------------------");
										System.out.println("0. Return to previous menu"); // manager menu
										System.out.println("1. Manages User");
										System.out.println("2. Manages Music");
										System.out.println("3. Manages Playlist");
										System.out.println("4. See statistics");
										int choice5 = scan.nextInt();
										if(choice5 == 0)
											break;
										else if(choice5 == 1) { // MANAGE USER
											
											while(true) {
												System.out.println("------------------------------------------");
												System.out.println("0. Return to previous menu");
												System.out.println("1. List All Users");
												System.out.println("2. Delete User");
												int choice6 = scan.nextInt();
												if(choice6 == 0) {
													break;
												}
												else if(choice6 == 1) { // SHOW ALL USER
													sql = "SELECT USER_ID , USER_NAME, USER_BD, USER_ADDR, USER_PN, REGISTER_DATE, USER_MANAGER FROM USER";
													pstmt = con.prepareStatement(sql);
													rs = pstmt.executeQuery();
													System.out.printf("%30s, %30s, %10s, %50s, %20s, %10s, %30s\n", "USER_ID", "USER_NAME","USER_BD","USER_ADDRESS","USER_PHONENUMBER","REGISTER_DATE","USER_MANAGER");
													while(rs.next()) {
														String userid = rs.getString(1);
														String username = rs.getString(2);
														String userbd = rs.getString(3);
														String useradd = rs.getString(4);
														String userph = rs.getString(5);
														String rd = rs.getString(6);
														String manager_name = rs.getString(7);
														System.out.printf("%30s, %30s, %10s, %50s, %20s, %10s, %30s\n", userid, username, userbd, useradd, userph, rd, manager_name);
													}
												}
												else if(choice6 == 2) { // DELETE USER
													System.out.print("Please type user id that you want to delete");
													String userid = scan.next();
													sql = "Select * from user where USER_ID = ?";
													pstmt = con.prepareStatement(sql);
													pstmt.setString(1,  userid);
													rs = pstmt.executeQuery();
													if(!rs.next()) {
														System.out.println("There is no such user.");
													}
													else {
														sql = "Delete from user where USER_ID = ?";
														pstmt = con.prepareStatement(sql);
														pstmt.setString(1, userid);
														pstmt.executeUpdate();
													}
												}
												else
													System.out.println("Please check your input! Your input is invalid");
											}
												
										}
										else if(choice5 == 2) { // MANAGE MUSIC
											
											while(true) {
												System.out.println("------------------------------------------");
												System.out.println("0. Return to previous menu");
												System.out.println("1. List All Musics");
												System.out.println("2. Add Music");
												System.out.println("3. Delete Music");
												int choice6 = scan.nextInt();
												if(choice6 == 0)
													break;
												else if(choice6 == 1) { // SHOW ALL MUSIC
													sql = "SELECT MUSIC_NUM, MUSIC_TITLE, MUSIC_GENRE, MUSIC_COMP, MUSIC_LYRICIST, MUSIC_COMPANY, MUSIC_SINGER FROM MUSIC";
													pstmt = con.prepareStatement(sql);
													rs = pstmt.executeQuery();
													System.out.printf("%10s, %30s, %30s, %30s, %30s, %30s, %30s\n", "MUISC_NUM", "MUSIC_TITLE", "MUSIC_GENRE", "MUSIC_COMP", "MUSIC_LYRICIST", "MUSIC_COMPANY", "MUSIC_SINGER");
													while(rs.next()) {
														int musicnum = rs.getInt("MUSIC_NUM");
														String musictitle = rs.getString("MUSIC_TITLE");
														String musicgenre = rs.getString("MUSIC_GENRE");
														String musiccomp = rs.getString("MUSIC_COMP");
														String musiclyric = rs.getString("MUSIC_LYRICIST");
														String musiccompany = rs.getString("MUSIC_COMPANY");
														String musicsinger = rs.getString("MUSIC_SINGER");
														System.out.printf("%10d, %30s, %30s, %30s, %30s, %30s, %30s\n", musicnum, musictitle, musicgenre, musiccomp, musiclyric, musiccompany, musicsinger);
													}
												}
												else if(choice6 == 2) { // ADD MUSIC
													System.out.print("Please type music title you want to add : ");
													String music_title = scan.next();
													System.out.print("Please type music genre you want to add : ");
													String music_genre = scan.next();
													System.out.print("Please type music composer you want to add : ");
													String music_comp = scan.next();
													System.out.print("Please type music lyricist you want to add : ");
													String music_lyric = scan.next();
													System.out.print("Please type company you want to add : ");
													String music_company = scan.next();
													System.out.print("Please type music singer ID you want to add : ");
													String singer = scan.next();
													sql = "Insert into music (MUSIC_TITLE, MUSIC_GENRE, MUSIC_COMP, MUSIC_LYRICIST, MUSIC_COMPANY, MUSIC_MANAGER, MUSIC_SINGER) values (?, ?,?, ?, ?, ?, ?)";
													pstmt = con.prepareStatement(sql);
													pstmt.setString(1, music_title);
													pstmt.setString(2, music_genre);
													pstmt.setString(3, music_comp);
													pstmt.setString(4, music_lyric);
													pstmt.setString(5, music_company);
													pstmt.setString(6, ID);
													pstmt.setString(7, singer);
													pstmt.executeUpdate();
												}
												else if(choice6 == 3) { // DELETE MUSIC
													System.out.print("Please type music title that you want to delete");
													String musictitle = scan.next();
													sql = "Select * from music where MUSIC_TITLE = ?";
													pstmt = con.prepareStatement(sql);
													pstmt.setString(1,  musictitle);
													rs = pstmt.executeQuery();
													if(!rs.next()) {
														System.out.println("There is no such music.");
													}
													else {
														sql = "Delete from music where MUSIC_TITLE = ?";
														pstmt = con.prepareStatement(sql);
														pstmt.setString(1, musictitle);
														pstmt.executeUpdate();
													}
												}
												else
													System.out.println("Please check your input! Your input is invalid");
											}
										}
										else if(choice5 == 3) { //Manage playlist
											
											while(true) {
												System.out.println("------------------------------------------");
												System.out.println("0. Return to previous menu");
												System.out.println("1. List All Playlist");
												System.out.println("2. Delete Playlist");
												int choice7 = scan.nextInt();
												if(choice7 == 0)
													break;
												else if(choice7 == 1) {
													sql = "SELECT * FROM PLAYLIST";
													pstmt = con.prepareStatement(sql);
													rs = pstmt.executeQuery();
													System.out.printf("%10s, %30s\n", "PLAYLIST_NUM", "USER_ID");
													while(rs.next()) {
														int playlist = rs.getInt("PLAYLIST_NUM");
														String userid = rs.getString("USER_ID");
														System.out.printf("%10s, %30d \n", playlist, userid);
													}
												}
												else if(choice7 == 2) {
													System.out.print("Please type playlist number that you want to delete");
													String playlistnum = scan.next();
													sql = "Select * from playlist where PLAYLIST_NUM = ?";
													pstmt = con.prepareStatement(sql);
													pstmt.setString(1,  playlistnum);
													rs = pstmt.executeQuery();
													if(!rs.next()) {
														System.out.println("There is no such playlist.");
													}
													else {
														sql = "Delete from playlist where PLAYLIST_NUM =  ?";
														pstmt = con.prepareStatement(sql);
														pstmt.setString(1, playlistnum);
														pstmt.executeUpdate();
													}
												}
												else
													System.out.println("Please check your input! Your input is invalid");
											}
										}
										else if(choice5 == 4) {
											while(true) {
												System.out.println("------------------------------------------");
												System.out.println("0. Return to previous menu");
												System.out.println("1. See fans");
												System.out.println("2. Check number of streaming");
												int choice = scan.nextInt();
												if(choice == 0)
													break;
												else if(choice == 1) {
													sql = "SELECT * FROM fans";
													pstmt = con.prepareStatement(sql);
													rs = pstmt.executeQuery();
													System.out.printf("%10s, %10s\n", "SINGER", "USER");
													while(rs.next()) {
														int singer = rs.getInt(1);
														int user_number = rs.getInt(2);
														System.out.printf("%10d, %10d \n", singer, user_number);
													}
												}
												else if(choice == 2) {
													sql = "SELECT USER, MUSIC, STREAMING FROM INTEREST";
													pstmt = con.prepareStatement(sql);
													rs = pstmt.executeQuery();
													System.out.printf("%30s, %10s, %10s \n", "USER", "MUSIC", "NUM OF STREAMING");
													while(rs.next()) {
														String user_id = rs.getString(1);
														int music_num = rs.getInt(2);
														int streaming = rs.getInt(3);
														System.out.printf("%30s, %10s, %10s \n", user_id, music_num, streaming);
													}
												}
											}
										}
										else {
											System.out.println("Please check your input! Your input is invalid");
										}
									}
								}
							}
							else {
								System.out.println("Are you really Manager? Please check your security number!");
							}
						}
						else if(choice2 == 2) { // REGISTER_MANAGER
							System.out.println("------------------------------------------");
							System.out.print("Name : ");
							String name = scan.next();
							System.out.print("ID : ");
							String ID = scan.next();
							System.out.print("PW : ");
							String PW = scan.next();
							System.out.print("Phone # : ");
							String Phone_Num = scan.next();
							System.out.print("Your birthdate : ");
							String BD = scan.next();
							System.out.print("Your home address : ");
							String Address = scan.next();
							System.out.print("Secrity # from Company : ");
							int Security_Num = scan.nextInt();
							if(Security_Num != Real_Security_Num){
								System.out.println("Are you really Manager? Please check your security number!");
							}
							else{
								String sql = "INSERT INTO manager VALUES ( ?, ?, ?, ?, ?, ?, ?)";
								pstmt=con.prepareStatement(sql);
								pstmt.setString(1,  ID);
								pstmt.setString(2,  PW);
								pstmt.setString(3,  name);
								pstmt.setString(4,  BD);
								pstmt.setString(5,  Address);
								pstmt.setString(6,  Phone_Num);
								pstmt.setInt(7, 0);
								pstmt.executeUpdate();
							}
						}
						else {
							System.out.println("You are input is invalid. Please check your input");
						}
					}
				}
				else if(choice0 == 2) { // USER MENU
					
					while(true) {
						System.out.println("------------------------------------------");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Login");
						System.out.println("2. Register");
						int choice = scan.nextInt();
						if(choice == 0)
							break;
						else if(choice == 1) {
							System.out.println("------------------------------------------");
							System.out.print("ID : ");
							String ID = scan.next();
							System.out.print("PW : ");
							String PW = scan.next();
							String sql = "SELECT * FROM USER WHERE USER_ID = ? AND USER_PW = ? ";
							pstmt = con.prepareStatement(sql);
							pstmt.setString(1, ID);
							pstmt.setString(2, PW);
							rs = pstmt.executeQuery();
							if(!rs.next()) {
								System.out.println("You typed wrong ID or PW.");
							}
							else{// LOGIN_USER
								
								while(true) {
									System.out.println("------------------------------------------");
									System.out.println("0. Return to previous menu");
									System.out.println("1. List Musics (You can search musics or add musics to your playlist in this section)");
									System.out.println("2. List PlayList I made (You can play music at this section)");
									int choice2 = scan.nextInt();
									if(choice2 == 0) {
										break;
									}
									else if(choice2 == 1) { // MUSIC_USER
										sql = "SELECT MUSIC_NUM, MUSIC_TITLE, MUSIC_GENRE, MUSIC_COMP, MUSIC_LYRICIST, MUSIC_COMPANY, MUSIC_SINGER FROM MUSIC";
										pstmt = con.prepareStatement(sql);
										rs = pstmt.executeQuery();
										System.out.printf("%10s, %30s, %30s, %30s, %30s, %30s, %30s\n", "MUISC_NUM", "MUSIC_TITLE", "MUSIC_GENRE", "MUSIC_COMP", "MUSIC_LYRICIST", "MUSIC_COMPANY", "MUSIC_SINGER");
										while(rs.next()) {
											int musicnum = rs.getInt("MUSIC_NUM");
											String musictitle = rs.getString("MUSIC_TITLE");
											String musicgenre = rs.getString("MUSIC_GENRE");
											String musiccomp = rs.getString("MUSIC_COMP");
											String musiclyric = rs.getString("MUSIC_LYRICIST");
											String musiccompany = rs.getString("MUSIC_COMPANY");
											String musicsinger = rs.getString("MUSIC_SINGER");
											System.out.printf("%10d, %30s, %30s, %30s, %30s, %30s, %30s\n" ,musicnum, musictitle, musicgenre, musiccomp, musiclyric, musiccompany, musicsinger);
										}
										
										while(true) {
											System.out.println("0. Return to previous menu");
											System.out.println("1. Add music to playlist");
											int choice3 = scan.nextInt();
											if(choice3 == 0)
												break;
											else if(choice3 == 1) {
												System.out.print("Do you want to search music by title? (Y/N) : ");
												String Y_OR_N = scan.next();
												if(Y_OR_N.equals("Y")) {
													System.out.print("You can type whole title or part of title. : ");
													String title_find = scan.next();
													sql = "SELECT MUSIC_NUM, MUSIC_TITLE, MUSIC_GENRE, MUSIC_COMP, MUSIC_LYRICIST, MUSIC_COMPANY, MUSIC_SINGER FROM MUSIC WHERE MUSIC_TITLE LIKE CONCAT('%',?,'%')";
													pstmt = con.prepareStatement(sql);
													pstmt.setString(1, title_find);
													rs = pstmt.executeQuery();
													System.out.printf("%10s, %30s, %30s, %30s, %30s, %30s, %30s\n", "MUSIC_NUM", "MUSIC_TITLE", "MUSIC_GENRE", "MUSIC_COMP", "MUSIC_LYRICIST", "MUSIC_COMPANY", "MUSIC_SINGER");
													while(rs.next()) {
														int musicnum = rs.getInt(1);
														String musictitle = rs.getString(2);
														String musicgenre = rs.getString("MUSIC_GENRE");
														String musiccomp = rs.getString("MUSIC_COMP");
														String musiclyric = rs.getString("MUSIC_LYRICIST");
														String musiccompany = rs.getString("MUSIC_COMPANY");
														String musicsinger = rs.getString("MUSIC_SINGER");
														System.out.printf("%10d, %30s, %30s, %30s, %30s, %30s, %30s\n", musicnum, musictitle, musicgenre, musiccomp, musiclyric, musiccompany, musicsinger);
													}
												}
												System.out.print("Type Music number you want to add");
												int MUSIC_NUM = scan.nextInt();
												System.out.print("Type the Playlist number that your wanted music to be in");
												String PLAYLIST_NUM = scan.next();
												System.out.print("Please type your interest (in percentage without %)");
												int interest = scan.nextInt();
												sql = "INSERT IGNORE INTO MUSIC_IN_PLAYLIST VALUES (?,?)";
												pstmt = con.prepareStatement(sql);
												pstmt.setString(1, PLAYLIST_NUM);
												pstmt.setInt(2, MUSIC_NUM);
												pstmt.executeUpdate();
												
												sql = "INSERT IGNORE INTO INTEREST VALUES (?, ?, ?, ?)";
												pstmt = con.prepareStatement(sql);
												pstmt.setString(1, ID);
												pstmt.setInt(2, MUSIC_NUM);
												pstmt.setInt(3, interest);
												pstmt.setInt(4, 0);
												pstmt.executeUpdate();
											}
										}
									}
									else if(choice2 == 2) { // PLAYLIST_USER
										sql = "SELECT PLAYLIST_NUM FROM PLAYLIST WHERE PLAYLIST_USER = ?";
										pstmt = con.prepareStatement(sql);
										pstmt.setString(1, ID);
										rs = pstmt.executeQuery();
										System.out.println("Here is your playlist(s)");
										System.out.println("PLAYLIST_NUMBER");
										while(rs.next()) {
											String PLAYLIST_NUMBER = rs.getString("PLAYLIST_NUM");
											System.out.println(PLAYLIST_NUMBER);
										}
										System.out.println("------------------------------------------");
										System.out.println("0. Return to previous menu");
										System.out.println("1. Play All musics in Playlist");
										System.out.println("2. Create Playlist");
										System.out.println("3. Delete Playlist");
										System.out.println("4. Delete musics in your playlist");
										int choice9 = scan.nextInt();
										if(choice9 == 1) {
											sql = "UPDATE INTEREST, PLAYLIST, MUSIC_IN_PLAYLIST SET STREAMING = STREAMING + 1 WHERE USER = ? AND PLAYLIST_USER = ? AND MUSIC = IN_MUSIC";
											pstmt = con.prepareStatement(sql);
											pstmt.setString(1, ID);
											pstmt.setString(2, ID);
											pstmt.executeQuery();
											sql = "SELECT MUSIC_SINGER FROM MUSIC, INTEREST WHERE INTEREST.USER = ? AND INTEREST.MUSIC = MUSIC.MUSIC_NUM GROUP BY MUSIC_SINGER HAVING SUM(STREAMING) >= 10000";
											pstmt = con.prepareStatement(sql);
											pstmt.setString(1, ID);
											rs = pstmt.executeQuery();
											while(rs.next()) {
												String musician = rs.getString(1);
												String sql2 = "INSERT IGNORE INTO FANS VALUES (?, ?)";
												PreparedStatement pstmt2 = con.prepareStatement(sql2);
												pstmt2.setString(1, musician);
												pstmt2.setString(2, ID);
												pstmt.executeQuery();
											}
										}
										else if(choice9 == 2) {
											sql = "INSERT INTO PLAYLIST (PLAYLIST_USER) VALUES (?)";
											pstmt = con.prepareStatement(sql);
											pstmt.setString(1, ID);
											pstmt.executeUpdate();
										}
										else if(choice9 == 3) {
											System.out.print("Please type Playlist number that you want to delete");
											int playlist_num = scan.nextInt();
											sql = "DELETE FROM PLAYLIST WHERE PLAYLIST_NUM = ? AND PLAYLIST_USER = ?";
											pstmt = con.prepareStatement(sql);
											pstmt.setInt(1, playlist_num);
											pstmt.setString(2, ID);
											pstmt.executeQuery();
										}
										else if(choice9 == 4) {
											System.out.print("Please type Playlistnumber that includes music that you want to delete. : ");
											int playlist_number = scan.nextInt();
											sql = "SELECT IN_MUSIC, MUSIC_TITLE FROM MUSIC_IN_PLAYLIST, MUSIC WHERE PLAYLIST_NUM = ? AND MUSIC_NUM = IN_MUSIC";
											pstmt = con.prepareStatement(sql);
											pstmt.setInt(1, playlist_number);
											rs = pstmt.executeQuery();
											System.out.println("Here is list of your musics in number " + playlist_number + " playlist");
											while(rs.next()) {
												int music_number = rs.getInt(1);
												String Music_title = rs.getString(2);
												System.out.printf("%10d", music_number);
												System.out.printf("%30s \n", Music_title);
											}
											System.out.print("Please type the music number you want to delete");
											int music_number = scan.nextInt();
											sql = "DELETE FROM MUSIC_IN_PLAYLIST WHERE PLAYLIST_NUM = ? AND IN_MUSIC = ?";
											pstmt = con.prepareStatement(sql);
											pstmt.setInt(1, playlist_number);
											pstmt.setInt(2, music_number);
											pstmt.executeQuery();
										}
									}
									else {
										System.out.println("Please check your input! Your input is invalid");
									}
								}
							}
						}
						else if(choice == 2) { // REGISTER_USER
							String manager = null;
							System.out.println("------------------------------------------");
							System.out.print("Name : ");
							String name = scan.next();
							System.out.print("ID : ");
							String ID = scan.next();
							System.out.print("PW : ");
							String PW = scan.next();
							System.out.print("Phone # : ");
							String Phone_Num = scan.next();
							System.out.print("Your birthdate : ");
							String BD = scan.next();
							System.out.print("Your home address : ");
							String Address = scan.next();
							SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
							Date time = new Date();
							String today = format.format(time);
							String find_manager = "SELECT MANAGER_ID FROM MANAGER ORDER BY MANAGES_NUMBER ASC";
							pstmt = con.prepareStatement(find_manager);
							rs = pstmt.executeQuery();
							if(rs.next())
								manager = rs.getString("MANAGER_ID");
							String sql = "INSERT INTO user VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
							pstmt=con.prepareStatement(sql);
							pstmt.setString(1,  ID);
							pstmt.setString(2,  PW);
							pstmt.setString(3,  name);
							pstmt.setString(4,  BD);
							pstmt.setString(5,  Address);
							pstmt.setString(6,  Phone_Num);
							pstmt.setString(7,  today);
							pstmt.setString(8,  manager);
							pstmt.executeUpdate();
						}
						else
							System.out.println("Please check your input! Your input is invalid");
					}
					
				}
				else if(choice0 == 3) { // SINGER MENU
					
					while(true) {
						System.out.println("------------------------------------------");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Login");
						System.out.println("2. Register");
						int choice = scan.nextInt();
						if(choice == 0)
							break;
						else if(choice == 1) { // LOGIN_SINGER
							System.out.println("------------------------------------------");
							System.out.print("ID : ");
							String ID = scan.next();
							System.out.print("PW : ");
							String PW = scan.next();
							String sql = "SELECT * FROM SINGER WHERE SINGER_ID = ? AND SINGER_PW = ? ";
							pstmt = con.prepareStatement(sql);
							pstmt.setString(1, ID);
							pstmt.setString(2, PW);
							rs = pstmt.executeQuery();
							if(!rs.next()) {
								System.out.println("You typed wrong ID or PW.");
							}
							else { // SUUCESS TO LOGIN
								
								while(true) {
									System.out.println("------------------------------------------");
									System.out.println("0. Return to previous menu");
									System.out.println("1. Check my fans");
									System.out.println("2. Manage my musics");
									int choice3 = scan.nextInt();
									if(choice3 == 0)
										break;
									else if(choice3 == 1) { // CHECK FANS
										sql = "SELECT USER FROM FANS WHERE SINGER = ?";
										pstmt = con.prepareStatement(sql);
										pstmt.setString(1,  ID);
										rs = pstmt.executeQuery();
										while(rs.next()) {
											String fan_ID = rs.getString("USER");
											System.out.println(fan_ID);
										}
									}
									else if(choice3 == 2) { // MANAGE MUSICS
										System.out.println("1. Add music");
										System.out.println("2. Delete music");
										int choice4 = scan.nextInt();
										if(choice4 == 1) { // ADD MUSIC_SINGER
											System.out.print("Please type your music title : ");
											String music_title = scan.next();
											System.out.print("Please type your music genre");
											String music_genre = scan.next();
											System.out.print("Please type your music composer");
											String music_comp = scan.next();
											System.out.print("Please type your music lyricist");
											String music_lyric = scan.next();
											System.out.print("Please type your company");
											String music_company = scan.next();
											System.out.print("Please type your musics manager ID");
											String manager = scan.next();
											sql = "Insert into music (MUSIC_TITLE, MUSIC_GENRE, MUSIC_COMP, MUSIC_LYRICIST, MUSIC_COMPANY, MUSIC_MANAGER, MUSIC_SINGER) values (?, ?,?, ?, ?, ?, ?)";
											pstmt = con.prepareStatement(sql);
											pstmt.setString(1, music_title);
											pstmt.setString(2, music_genre);
											pstmt.setString(3, music_comp);
											pstmt.setString(4, music_lyric);
											pstmt.setString(5, music_company);
											pstmt.setString(6, manager);
											pstmt.setString(7, ID);
											pstmt.executeUpdate();
										}
										else if(choice4 == 2) { // DELETE MUSIC_SINGER
											System.out.print("Please type your music title that you want to delete");
											String music_title = scan.next();
											sql = "Select * from music where MUSIC_TITLE = ? and MUSIC_SINGER = ?";
											pstmt = con.prepareStatement(sql);
											pstmt.setString(1,  music_title);
											pstmt.setString(2,  ID);
											rs = pstmt.executeQuery();
											if(!rs.next()) {
												System.out.println("There is no such music.");
											}
											else {
												sql = "Delete from music where MUSIC_TITLE = ? and MUSIC_SINGER = ?";
												pstmt = con.prepareStatement(sql);
												pstmt.setString(1, music_title);
												pstmt.setString(2, ID);
												pstmt.executeUpdate();
											}
										}
										else {
											System.out.println("Please check your input! It will be moved to privious section");
										}
									}
								}
							}
						}
						else if (choice == 2) { // REGISTER_SINGER
							System.out.println("------------------------------------------");
							System.out.print("Name : ");
							String name = scan.next();
							System.out.print("ID : ");
							String ID = scan.next();
							System.out.print("PW : ");
							String PW = scan.next();
							System.out.print("Your birthdate : ");
							String BD = scan.next();
							String sql = "INSERT INTO singer VALUES ( ?, ?, ?, ?)";
							pstmt=con.prepareStatement(sql);
							pstmt.setString(1,  ID);
							pstmt.setString(2,  PW);
							pstmt.setString(3,  name);
							pstmt.setString(4,  BD);
							pstmt.executeUpdate();
							choice = 4;
							continue;
						}
						else {
							System.out.println("Your input is invalid. Please check your input!");
						}
					}
				}
				else if(choice0 == 0) {
					System.out.println("The programm will be shut down. Thanks for using.");
					break;
				}
				else {
					System.out.println("You are input is invalid. Please check your input.");
				}
			}
			//rs.close();
			//pstmt.close();
			//con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return;
	}

}
