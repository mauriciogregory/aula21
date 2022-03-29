package com.springDh.aula21.dao.impl;

import com.springDh.aula21.dao.IDao;
import com.springDh.aula21.dao.config.DB;
import com.springDh.aula21.model.Dentista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DentistaDaoMySql implements IDao<Dentista> {

    private final Connection conn;

    public DentistaDaoMySql() {
        this.conn = DB.getConnection();
    }

    @Override
    public Dentista salvar(Dentista dentista) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("""
                    INSERT INTO dentistas
                    (nome, email, numMatricula, atendeConvenio)
                    VALUES (?, ?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, dentista.getNome());
            st.setString(2, dentista.getEmail());
            st.setInt(3, dentista.getNumMatricula());
            st.setInt(4, dentista.getAtendeConvenio());

            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next())
                dentista.setId(rs.getInt(1));

            return dentista;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return null;
    }

    @Override
    public Optional<Dentista> buscar(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("""
                    Select * from dentistas
                    WHERE id = ?;
                    """);

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                return Optional.of(instantieteDentista(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DB.closeResultSet(rs);
            DB.closeResultSet(rs);
        }


        return Optional.empty();
    }

    @Override
    public void excluir(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("""
                    DELETE FROM dentistas
                    WHERE id = ?;
                    """);

            st.setInt(1 , id);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public List<Dentista> buscarTodos() {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("""
                    SELECT * FROM dentistas;
                    """);

            List<Dentista> dentistas = new ArrayList<>();

            rs = st.executeQuery();

            while (rs.next()){
                dentistas.add(instantieteDentista(rs));
            }

            return dentistas;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Dentista atualizar(Dentista dentista) {

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("""
                    UPDATE dentistas
                    SET nome = ?, email = ?, numMatricula = ?,  atendeConvenio = ?
                    WHERE id = ?;
                    """);

            st.setString(1, dentista.getNome());
            st.setString(2, dentista.getEmail());
            st.setInt(3, dentista.getNumMatricula());
            st.setInt(4, dentista.getAtendeConvenio());
            st.setInt(5, dentista.getId());

            int rows = st.executeUpdate();

            if (rows == 1){
                return dentista;
            }else {
                System.out.println("Nenhum registro encontrado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DB.closeStatement(st);
        }

        return null;
    }

    private Dentista instantieteDentista(ResultSet rs) throws SQLException {
        Dentista dentista = new Dentista();
        dentista.setId(rs.getInt("id"));
        dentista.setNome(rs.getString("nome"));
        dentista.setEmail(rs.getString("email"));
        dentista.setAtendeConvenio(rs.getInt("atendeConvenio"));
        dentista.setNumMatricula(rs.getInt("numMatricula"));
        return dentista;

    }
}
