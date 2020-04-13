package com.company.dao;

import com.company.model.User;
import com.company.model.UserRole;
import com.company.service.ServiceUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicReference;

public class UserDao {
    public static String delim = "-";
    private String DB_Destination = "database/users/userDatabase.txt";

    public static Logger log = LogManager.getLogger();

    private User parseUser(String input) {
        StringTokenizer st = new StringTokenizer(input, delim);
        long id = Long.parseLong(st.nextToken());
        String login = st.nextToken();
        String password = st.nextToken();
        String email = st.nextToken();
        UserRole role = UserRole.valueOf(st.nextToken());
        return new User(id, login, password, email, role);
    }

    private long getLastID() {
        long toReturn = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(DB_Destination))) {
            String tempLine = "";
            while ((tempLine = reader.readLine()) != null) {
                toReturn = parseUser(tempLine).getId();
            }
        } catch (IOException e) {
            log.fatal(e);
        }
        return toReturn;
    }

    public List<User> getAll() {
        List<User> toReturn = new ArrayList<User>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DB_Destination))) {
            String tempLine = "";
            while ((tempLine = reader.readLine()) != null) {
                toReturn.add(parseUser(tempLine));
            }
            return toReturn;
        } catch (IOException e) {
            log.fatal(e);
        }
        return new ArrayList<User>();
    }

    public User getById(long inputId) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(DB_Destination))) {
//            String tempLine = "";
//            while ((tempLine = reader.readLine()) != null) {
//                User temp = parseUser(tempLine);
//                if (temp.getId() == inputId) {
//                    return temp;
//                }
//            }
//        } catch (IOException e) {
//            log.fatal(e);
//        }
        List<User> userList = getAll();
        for(User userIter : userList) {
            if(userIter.getId() == inputId) {
                return userIter;
            }
        }
        return null;
    }

    public User getByLogin(String inputLogin) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(DB_Destination))) {
//            String tempLine = "";
//            while ((tempLine = reader.readLine()) != null) {
//                User temp = parseUser(tempLine);
//                if (temp.getLogin().equals(inputLogin)) {
//                    return temp;
//                }
//            }
//        } catch (IOException e) {
//            log.fatal(e);
//        }
        List<User> userList = getAll();
        for(User userIter : userList) {
            if(userIter.getLogin().equals(inputLogin)) {
                return userIter;
            }
        }
        return null;
    }

    public void createUser(String login, String password, String email, UserRole role) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DB_Destination, true))) {
            User newUser = new User(getLastID() + 1, login, password, email, role);
            writer.write(newUser.toStringFileFormat());
            writer.newLine();
        } catch (IOException ignored) {
        }
    }

    public void recreateUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DB_Destination, true))) {
            writer.write(user.toStringFileFormat());
            writer.newLine();
        } catch (IOException ignored) {
        }
    }

    public void deleteAll() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DB_Destination))) {
            writer.write("");
            DaoUtility.courseDao.removeAllUsersFromAllCourses();
        } catch (IOException ignored) {
        }
    }

    public void deleteById(long inputId) {
        List<User> users = getAll();
        deleteAll();
        for (User userIter : users) {
            if (userIter.getId() != inputId) {
                recreateUser(userIter);
                DaoUtility.courseDao.removeUserFromAllCourses(inputId);
            }
        }
    }

    public void updateById (long inputId, User newUser) {
        List<User> users = getAll();
        deleteAll();
        for (User userIter : users) {
            if(userIter.getId() == inputId) {
                userIter.setLogin(newUser.getLogin());
                userIter.setPassword(newUser.getPassword());
                userIter.setEmail(newUser.getEmail());
                userIter.setRole(newUser.getRole());
            }
            recreateUser(userIter);
        }
    }
}
