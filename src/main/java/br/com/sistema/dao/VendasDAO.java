/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Clientes;
import br.com.sistema.model.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class VendasDAO {

    private Connection con;

    public VendasDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    //Cadastrar Venda
    public void cadastrarVenda(Vendas obj) {
        try {

            String sql = "insert into tb_vendas (cliente_id, data_venda,total_venda, observacoes, data_venc_pgto, condicao_pgto, data_pgto) values (?,?,?,?,?,?,?)";
            //2 passo - conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getCliente().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setString(5, obj.getData_vencimento());
            stmt.setString(6, obj.getCondicao());
            stmt.setDouble(3, obj.getTotal_venda());
            stmt.setString(4, obj.getObs());
            stmt.setString(7, obj.getData_pgto());
            

            stmt.execute();
            stmt.close();

        } catch (Exception erro) {

            JOptionPane.showMessageDialog(null, "Erro : " + erro);

        }

    }

    //Retorna a ultima venda
    public int retornaUltimaVenda() {
        try {
            int idvenda = 0;

            String sql = "select max(id) id from tb_vendas";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Vendas p = new Vendas();

                p.setId(rs.getInt("id"));
                idvenda = p.getId();
            }

            return idvenda;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Metodo que filtra Vendas por Datas de Realização
    public List<Vendas> listarVendasPorPeriodo(LocalDate data_inicio, LocalDate data_fim) {
        try {

            //1 passo criar a lista
            List<Vendas> lista = new ArrayList<>();

            //2 passo - criar o sql , organizar e executar.
            String sql = "select v.id ,  date_format(v.data_venda,'%d/%m/%Y') as data_formatada , c.nome, v.total_venda, v.observacoes, date_format(v.data_venc_pgto,'%d/%m/%Y') as data_venc_pgto_f, date_format(v.data_pgto,'%d/%m/%Y') as data_pgto_f  from tb_vendas as v  "
                    + " inner join tb_clientes as c on(v.cliente_id = c.id) where v.data_venda BETWEEN ? AND ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, data_inicio.toString());
            stmt.setString(2, data_fim.toString());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vendas obj = new Vendas();
                Clientes c = new Clientes();

                obj.setId(rs.getInt("v.id"));
                obj.setData_venda(rs.getString("data_formatada"));
                c.setNome(rs.getString("c.nome"));
                obj.setTotal_venda(rs.getDouble("v.total_venda"));
                obj.setObs(rs.getString("v.observacoes"));
                try{
                    obj.setData_pgto(rs.getString("data_pgto_f"));
                } catch(SQLException e) {}
                if(obj.getData_pgto() == null){
                    obj.setData_pgto("Não pago");
                }
                obj.setData_vencimento(rs.getString("data_venc_pgto_f"));
                
                obj.setCliente(c);

                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "Erro :" + erro);
            return null;
        }

    }

	//Metodo que filtra Vendas por Datas de Vencimento
    public List<Vendas> listarVencimentosPorPeriodo(LocalDate data_inicio, LocalDate data_fim) {
        try {

            //1 passo criar a lista
            List<Vendas> lista = new ArrayList<>();

            //2 passo - criar o sql , organizar e executar.
            String sql = "select v.id ,  date_format(v.data_venda,'%d/%m/%Y') as data_formatada , c.nome, v.total_venda, v.observacoes  from tb_vendas as v  "
                    + " inner join tb_clientes as c on(v.cliente_id = c.id) where v.data_venc_pgto BETWEEN ? AND ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, data_inicio.toString());
            stmt.setString(2, data_fim.toString());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vendas obj = new Vendas();
                Clientes c = new Clientes();

                obj.setId(rs.getInt("v.id"));
                obj.setData_vencimento(rs.getString("data_formatada"));
                c.setNome(rs.getString("c.nome"));
                obj.setTotal_venda(rs.getDouble("v.total_venda"));
                obj.setObs(rs.getString("v.observacoes"));

                obj.setCliente(c);

                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "Erro :" + erro);
            return null;
        }

    }


    //Metodo que calcula total da venda por data
    public double retornaTotalVendaPorData(LocalDate data_venda) {
        try {

            double totalvenda = 0;

            String sql = "select sum(total_venda) as total from tb_vendas where data_venda = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, data_venda.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                totalvenda = rs.getDouble("total");
            }

            return totalvenda;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    public Vendas buscaVendaporid(int id) {
        try {
            //1 passo - criar o sql , organizar e executar.
            String sql = "select * from tb_vendas where id =  ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            Vendas obj = new Vendas();

            if (rs.next()) {
                obj.setId(rs.getInt("id"));
                obj.setData_venda(rs.getString("data_venda"));
                obj.setData_vencimento(rs.getString("data_venc_pgto"));
                obj.setCondicao(rs.getString("condicao_pgto"));
                obj.setData_pgto(rs.getString("data_pgto"));
                obj.setTotal_venda(rs.getDouble("total_venda"));
            }

            return obj;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Venda não encontrado!");
            return null;
        }
    }

    public void pagamentoVenda(Vendas venda){
        try {

            String sql = "update tb_vendas  set observacoes=?, data_pgto=?, condicao_pgto=?  where id=?";
            //2 passo - conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
        
            stmt.setString(1, venda.getObs());
            stmt.setString(2, venda.getData_pgto());
            stmt.setString(3, venda.getCondicao());
            stmt.setInt(4, venda.getId());

            stmt.execute();
            stmt.close();

        } catch (Exception erro) {

            JOptionPane.showMessageDialog(null, "Erro : " + erro);

        }
    
    }
}

