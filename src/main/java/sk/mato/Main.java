package sk.mato;

import sk.mato.db.DBContactService;
import sk.mato.db.HikariCPDataSource;
import sk.mato.service.CRUDManager;

import java.sql.*;


public class Main
{
    public static void main( String[] args )
    {
        new CRUDManager().printOptions();
    }
}
