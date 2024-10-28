
package View;

import connect.Conexao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;



public class Home_Funcionario extends javax.swing.JFrame {

    
    public Home_Funcionario() {
        initComponents();
        P14.setVisible (false);
        carregarClientesNaTabela();
        carregarAgendamentos();
        carregarAgendamentosFeitos();
        carregarPromocoesNaTabela();
        carregarFeedbacks();
        
         jCheckBoxLavagemInterna.addItemListener((ItemEvent e) -> {
             calcularTotal();
        });

jCheckBoxLavagemExterna.addItemListener((ItemEvent e) -> {
    calcularTotal();
        });

jCheckBoxPolimento.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        calcularTotal();
    }
});

jCheckBoxLavagemCompleta.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        calcularTotal();
    }
});

jCheckBoxLavagemRodape.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        calcularTotal();
    }
});

jCheckBoxHibernizacao.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        calcularTotal();
    }
});

         

    }
    
    
    
    
    
    
    private javax.swing.JTextField jTextFieldId = new javax.swing.JTextField();


// Preços dos serviços
private final Map<String, Integer> precosServicos = Map.of(
    "Lavagem Interna", 1000,
    "Lavagem Externa", 800,
    "Polimento e Enceramento", 10000,
    "Lavagem Completa", 1700,
    "Lavagem de Rodas e Pneus", 500,
    "Hibernização e tratamento de bancos", 1500
);

private void calcularTotal() {
    double total = 0.0;

    // Verificando o tipo de viatura no JComboBox e adicionando o preço correspondente
    String tipoViatura = jComboBoxTipoViatura.getSelectedItem().toString();
    switch (tipoViatura) {
        case "Pesado":
            total += 1100;
            break;
        case "Especial":
            total += 700;
            break;
        case "Ligeiro Pessoal":
            total += 500;
            break;
        case "Motocicleta":
            total += 250;
            break;
        default:
            JOptionPane.showMessageDialog(this, "Tipo de viatura desconhecido.");
            return;
    }
    
    jComboBoxTipoViatura.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        calcularTotal();
    }
});
    
    
    String tipoMotor = jComboBoxTipoMotor.getSelectedItem().toString();
    switch (tipoMotor) {
        case "VVTI":
            total += 250;
            break;
        case "V2":
        case "V3":
            total += 300;
            break;
        case "V6":
            total += 400;
            break;
        case "V8":
            total += 600;
            break;
    }
    
    
jComboBoxTipoMotor.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        calcularTotal(); 
    }
});


    // Verificando os checkboxes e adicionando os preços dos serviços
    if (jCheckBoxLavagemInterna.isSelected()) {
        total += 1000; 
    }
    if (jCheckBoxLavagemExterna.isSelected()) {
        total += 800; 
    }
    if (jCheckBoxPolimento.isSelected()) {
        total += 10000; 
    }
    if (jCheckBoxLavagemCompleta.isSelected()) {
        total += 1700; 
    }
    if (jCheckBoxLavagemRodape.isSelected()) {
        total += 500; 
    }
    if (jCheckBoxHibernizacao.isSelected()) {
        total += 1500; 
    }

    // Atualizando o campo de total com o resultado final
    jTextFieldTotal.setText(String.valueOf(total));
}
    
    
    
    
    

    private void carregarClientesNaTabela() {
    try {
        java.sql.Connection con = Conexao.connect();
        String sql = "SELECT * FROM clientes";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) clienteTable.getModel();
        model.setRowCount(0);  

        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("sexo"),
                rs.getString("email"),
                rs.getString("contacto"),
                rs.getString("morada"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("matricula"),
                rs.getString("status")
            };
            model.addRow(row);
        }

        rs.close();
        pst.close();
        con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar Cliente: " + e.getMessage());
    }
}
    
    
  private void carregarPromocoesNaTabela() {
    try {
        java.sql.Connection con = Conexao.connect(); // Conecta ao banco de dados
        String sql = "SELECT * FROM promocoes_carwash"; // Seleciona todas as promoções
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tabelaPromocoes.getModel(); // Obtém o modelo da tabela
        model.setRowCount(0);  // Limpa a tabela antes de carregar os dados

        while (rs.next()) {
            // Prepara a linha com os dados da promoção
            Object[] row = {
                rs.getString("promocao"), // Coluna da promoção
                rs.getDouble("desconto"),       // Coluna do desconto
                rs.getDate("validade")          // Coluna da validade
            };
            model.addRow(row); // Adiciona a linha ao modelo da tabela
        }

        rs.close(); // Fecha o ResultSet
        pst.close(); // Fecha o PreparedStatement
        con.close(); // Fecha a conexão
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar Promoções: " + e.getMessage());
    }
}
   
    
    
    
    
    private void carregarAgendamentos() {
    DefaultTableModel model = (DefaultTableModel) jTableAgendamentos.getModel();
    model.setRowCount(0); 

    try {
        java.sql.Connection con = Conexao.connect();
        String sql = "SELECT * FROM agendamentos"; 
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            // Adicionando os dados à tabela
            model.addRow(new Object[]{
                rs.getInt("id"), // ID
                rs.getString("marca"),
                rs.getString("placa"),
                rs.getString("proprietario"),
                rs.getString("contacto"),
                rs.getString("tipo_viatura"),
                rs.getString("hora_entrada"),
                rs.getString("hora_saida"),
                rs.getDouble("total_servicos")
            });
        }
        pst.close();
        con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar agendamentos: " + e.getMessage());
    }
}
    
    
    
    
    private void carregarAgendamentosFeitos() {
    DefaultTableModel modelFeitos = (DefaultTableModel) jTableFeitos.getModel();
    
    try {
        java.sql.Connection con = Conexao.connect(); // Conecta ao banco de dados
        
        String sql = "SELECT * FROM agendamentos_feitos";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Object[] rowData = new Object[9]; // 9 colunas na JTable
            rowData[0] = rs.getInt("id");
            rowData[1] = rs.getString("marca");
            rowData[2] = rs.getString("placa");
            rowData[3] = rs.getString("proprietario");
            rowData[4] = rs.getString("contacto");
            rowData[5] = rs.getString("tipo_viatura");
            rowData[6] = rs.getTime("hora_entrada");
            rowData[7] = rs.getTime("hora_saida");
            rowData[8] = rs.getBigDecimal("total_servicos");

            modelFeitos.addRow(rowData); // Adiciona à JTableFeitos
        }
        
        rs.close();
        pst.close();
        con.close();
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar agendamentos feitos: " + e.getMessage());
    }
}
    
 // Método para carregar todos os registros do banco de dados e exibir na tabela
    private void carregarFeedbacks() {
        DefaultTableModel model = (DefaultTableModel) feedbackTable.getModel();
        model.setRowCount(0); // Limpar a tabela antes de carregar os dados

        String url = "jdbc:mysql://localhost:3306/testesdb"; // Ajuste conforme necessário
        String user = "root";
        String password = ""; // Substitua pela senha correta

        // Verifica se o driver JDBC está carregado
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Para MySQL 8.0 ou superior
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver JDBC não encontrado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Conectar ao banco de dados e carregar os dados
        String sql = "SELECT cliente, data, comentario FROM feedbacks";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Itera sobre os resultados e adiciona os dados na tabela
            while (resultSet.next()) {
                String cliente = resultSet.getString("cliente");
                java.sql.Date data = resultSet.getDate("data");
                String comentario = resultSet.getString("comentario");

                model.addRow(new Object[]{cliente, data, comentario});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar feedbacks: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Cadastrarcliente = new javax.swing.JLabel();
        feedback = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        relatorio = new javax.swing.JLabel();
        Stock = new javax.swing.JLabel();
        AgendamentosPendentes = new javax.swing.JLabel();
        Agendamentos = new javax.swing.JLabel();
        Ver_dados = new javax.swing.JLabel();
        AgendamentosFeitos = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        Paineis = new javax.swing.JTabbedPane();
        P3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        P5 = new javax.swing.JPanel();
        P10 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        clienteTable = new javax.swing.JTable();
        jButtonEditar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        pesquisajTextField = new javax.swing.JTextField();
        P7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jCheckBoxLavagemInterna = new javax.swing.JCheckBox();
        jCheckBoxLavagemExterna = new javax.swing.JCheckBox();
        jCheckBoxPolimento = new javax.swing.JCheckBox();
        jCheckBoxLavagemCompleta = new javax.swing.JCheckBox();
        jCheckBoxHibernizacao = new javax.swing.JCheckBox();
        jTextFieldMarca = new javax.swing.JTextField();
        jTextFieldPlaca = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButtonSalvarAgendamento = new javax.swing.JButton();
        jTextFieldTotal = new javax.swing.JLabel();
        jCheckBoxLavagemRodape = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldProprietario = new javax.swing.JTextField();
        jComboBoxTipoViatura = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldHoraEntrada = new javax.swing.JTextField();
        jTextFieldHoraSaida = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldContacto = new javax.swing.JTextField();
        jComboBoxTipoMotor = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        P12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableFeitos = new javax.swing.JTable();
        P4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        sexo1 = new javax.swing.JLabel();
        nomeField = new javax.swing.JTextField();
        email1 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        contacto1 = new javax.swing.JLabel();
        moradaField = new javax.swing.JTextField();
        Cadastrar1 = new javax.swing.JButton();
        P13 = new javax.swing.JPanel();
        marca1 = new javax.swing.JLabel();
        modelo1 = new javax.swing.JLabel();
        matricula1 = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        marcaField = new javax.swing.JTextField();
        modeloField = new javax.swing.JTextField();
        jTextFieldMatricula = new javax.swing.JTextField();
        morada1 = new javax.swing.JLabel();
        contactoField = new javax.swing.JTextField();
        nome1 = new javax.swing.JLabel();
        sexoComboBox = new javax.swing.JComboBox<>();
        P14 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        P15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        nomeProdutoField = new javax.swing.JTextField();
        dataValidadeField = new javax.swing.JTextField();
        categoriaComboBox = new javax.swing.JComboBox<>();
        quantidadeSpinner = new javax.swing.JSpinner();
        AdicionarProduto = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        validadeTextArea = new javax.swing.JTextArea();
        P2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        feedbackTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        comentarioArea = new javax.swing.JTextArea();
        viewDetailsButton = new javax.swing.JButton();
        clienteLabel = new javax.swing.JLabel();
        dataLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        P16 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        promocaoField = new javax.swing.JTextField();
        descontoField = new javax.swing.JTextField();
        validadeField = new javax.swing.JTextField();
        adicionar = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelaPromocoes = new javax.swing.JTable();
        editar = new javax.swing.JButton();
        excluir = new javax.swing.JButton();
        editar1 = new javax.swing.JButton();
        excluir1 = new javax.swing.JButton();
        P17 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableAgendamentos = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButtonSalvarEdicao = new javax.swing.JButton();
        jButtonRemover1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(1080, 680));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Imagens/ABSA_Car_wash-removebg-preview (1).png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 206, 153));

        Cadastrarcliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Cadastrarcliente.setForeground(new java.awt.Color(255, 255, 255));
        Cadastrarcliente.setText("    Cadastrar Cliente");
        Cadastrarcliente.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Cadastrarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cadastrarcliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CadastrarclienteMouseClicked(evt);
            }
        });
        jPanel1.add(Cadastrarcliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 200, 40));

        feedback.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        feedback.setForeground(new java.awt.Color(255, 255, 255));
        feedback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8_comments_30px.png"))); // NOI18N
        feedback.setText("Feedback");
        feedback.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        feedback.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        feedback.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                feedbackMouseClicked(evt);
            }
        });
        jPanel1.add(feedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 640, 200, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8_pricing_30px_2.png"))); // NOI18N
        jLabel6.setText("      Promocoes");
        jLabel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 590, 200, 40));

        relatorio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        relatorio.setForeground(new java.awt.Color(255, 255, 255));
        relatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8_business_report_30px.png"))); // NOI18N
        relatorio.setText("      Relatório");
        relatorio.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        relatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        relatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatorioMouseClicked(evt);
            }
        });
        jPanel1.add(relatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, 200, 40));

        Stock.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Stock.setForeground(new java.awt.Color(255, 255, 255));
        Stock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8_wallet_30px.png"))); // NOI18N
        Stock.setText("     Gestão de Estoque");
        Stock.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Stock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Stock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StockMouseClicked(evt);
            }
        });
        jPanel1.add(Stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 200, 40));

        AgendamentosPendentes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AgendamentosPendentes.setForeground(new java.awt.Color(255, 255, 255));
        AgendamentosPendentes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8_timesheet_30px.png"))); // NOI18N
        AgendamentosPendentes.setText("     Agendamentos Pendentes");
        AgendamentosPendentes.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        AgendamentosPendentes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AgendamentosPendentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AgendamentosPendentesMouseClicked(evt);
            }
        });
        jPanel1.add(AgendamentosPendentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 200, 40));

        Agendamentos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Agendamentos.setForeground(new java.awt.Color(255, 255, 255));
        Agendamentos.setText("    Agendamentos");
        Agendamentos.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Agendamentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Agendamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AgendamentosMouseClicked(evt);
            }
        });
        jPanel1.add(Agendamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 200, 40));

        Ver_dados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Ver_dados.setForeground(new java.awt.Color(255, 255, 255));
        Ver_dados.setText("    Ver  Dados Clientes");
        Ver_dados.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Ver_dados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Ver_dados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Ver_dadosMouseClicked(evt);
            }
        });
        jPanel1.add(Ver_dados, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 200, 40));

        AgendamentosFeitos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AgendamentosFeitos.setForeground(new java.awt.Color(255, 255, 255));
        AgendamentosFeitos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8_task_completed_30px_1.png"))); // NOI18N
        AgendamentosFeitos.setText("     Agendamentos Feitos");
        AgendamentosFeitos.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        AgendamentosFeitos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AgendamentosFeitosMouseClicked(evt);
            }
        });
        jPanel1.add(AgendamentosFeitos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 200, 40));

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/home-40.png"))); // NOI18N
        jButton2.setText("HOME");
        jButton2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setMargin(new java.awt.Insets(2, 20, 3, 2));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 40, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 730));

        P3.setBackground(new java.awt.Color(0, 204, 204));
        P3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("BEM-VINDO FUNCIONÁRIO");
        P3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 330, 70));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Imagens/pngwing.com 2.png"))); // NOI18N
        P3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 1110, 690));

        Paineis.addTab("tab1", P3);

        P5.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout P5Layout = new javax.swing.GroupLayout(P5);
        P5.setLayout(P5Layout);
        P5Layout.setHorizontalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3470, Short.MAX_VALUE)
        );
        P5Layout.setVerticalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );

        Paineis.addTab("tab2", P5);

        P10.setBackground(new java.awt.Color(18, 181, 185));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Clientes Cadastrados");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(jLabel3)
                .addContainerGap(599, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        clienteTable.setBackground(new java.awt.Color(204, 255, 255));
        clienteTable.setForeground(new java.awt.Color(0, 0, 0));
        clienteTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "nome", "sexo", "email", "contacto", "morada", "marca", "modelo", "matricula", "status"
            }
        ));
        clienteTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
        jScrollPane2.setViewportView(clienteTable);

        jButtonEditar.setBackground(new java.awt.Color(51, 255, 51));
        jButtonEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonEditar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonEditar.setText("Editar");
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 0, 0));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 0, 0));
        jButton5.setText("Excluir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Pesquisar:");

        pesquisajTextField.setBackground(new java.awt.Color(255, 255, 255));
        pesquisajTextField.setForeground(new java.awt.Color(0, 0, 0));
        pesquisajTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisajTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout P10Layout = new javax.swing.GroupLayout(P10);
        P10.setLayout(P10Layout);
        P10Layout.setHorizontalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P10Layout.createSequentialGroup()
                .addGroup(P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(404, 404, 404)
                        .addComponent(jButtonEditar)
                        .addGap(93, 93, 93)
                        .addComponent(jButton5))
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1057, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(2234, Short.MAX_VALUE))
        );
        P10Layout.setVerticalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pesquisajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addGroup(P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEditar)
                    .addComponent(jButton5))
                .addGap(77, 77, 77))
        );

        Paineis.addTab("tab10", P10);

        P7.setBackground(new java.awt.Color(0, 204, 153));
        P7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Marca");
        P7.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Placa");
        P7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, -1, 28));

        jCheckBoxLavagemInterna.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemInterna.setText("Lavagem Interna");
        P7.add(jCheckBoxLavagemInterna, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, -1, -1));

        jCheckBoxLavagemExterna.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemExterna.setText("Lavagem Externa");
        P7.add(jCheckBoxLavagemExterna, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, -1, -1));

        jCheckBoxPolimento.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxPolimento.setText("Polimento e Enceramento");
        P7.add(jCheckBoxPolimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 355, -1, -1));

        jCheckBoxLavagemCompleta.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemCompleta.setText("Lavagem Completa");
        P7.add(jCheckBoxLavagemCompleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 400, -1, -1));

        jCheckBoxHibernizacao.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxHibernizacao.setText("Hibernização e Tratamento de Bancos");
        P7.add(jCheckBoxHibernizacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 360, -1, -1));

        jTextFieldMarca.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(jTextFieldMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 138, -1));

        jTextFieldPlaca.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPlacaActionPerformed(evt);
            }
        });
        P7.add(jTextFieldPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 138, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("AGENDAMENTOS");
        P7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 28, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Valor A pagar");
        P7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 560, -1, -1));

        jButtonSalvarAgendamento.setBackground(new java.awt.Color(255, 255, 255));
        jButtonSalvarAgendamento.setForeground(new java.awt.Color(0, 0, 0));
        jButtonSalvarAgendamento.setText("Salvar");
        jButtonSalvarAgendamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarAgendamentoActionPerformed(evt);
            }
        });
        P7.add(jButtonSalvarAgendamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(447, 510, -1, -1));

        jTextFieldTotal.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldTotal.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldTotal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        P7.add(jTextFieldTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 610, 100, 25));

        jCheckBoxLavagemRodape.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemRodape.setText("Lavagem Externa");
        P7.add(jCheckBoxLavagemRodape, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 400, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Contacto");
        P7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, -1, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Proprietário");
        P7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, -1, -1));

        jTextFieldProprietario.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(jTextFieldProprietario, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 120, -1));

        jComboBoxTipoViatura.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxTipoViatura.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxTipoViatura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pesado", "Especial", "Ligeiro Pessoal", "Motocicleta" }));
        P7.add(jComboBoxTipoViatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 130, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Hora de Entrada");
        P7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 96, -1, 20));

        jTextFieldHoraEntrada.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldHoraEntrada.setForeground(new java.awt.Color(0, 0, 0));
        P7.add(jTextFieldHoraEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, 120, -1));

        jTextFieldHoraSaida.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldHoraSaida.setForeground(new java.awt.Color(0, 0, 0));
        P7.add(jTextFieldHoraSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 140, 120, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Hora de Saida");
        P7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 140, -1, -1));

        jTextFieldContacto.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldContacto.setForeground(new java.awt.Color(0, 0, 0));
        P7.add(jTextFieldContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 120, -1));

        jComboBoxTipoMotor.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxTipoMotor.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxTipoMotor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "VVTI", "V2 e V3", "V6", "V8" }));
        P7.add(jComboBoxTipoMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 280, 110, -1));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Categoria");
        P7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 60, 20));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Tipo de Motor");
        P7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 280, -1, -1));

        Paineis.addTab("tab4", P7);

        P12.setBackground(new java.awt.Color(51, 51, 51));
        P12.setToolTipText("");
        P12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableFeitos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "marca", "placa", "proprietario", "contacto", "tipo_viatura", "hora_entrada", "hora_saida", "total_servicos"
            }
        ));
        jScrollPane3.setViewportView(jTableFeitos);

        P12.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 1030, -1));

        Paineis.addTab("tab5", P12);

        P4.setBackground(new java.awt.Color(0, 204, 204));
        P4.setForeground(new java.awt.Color(0, 0, 0));
        P4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("CADASTRO DE CLIENTES");
        P4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(413, 6, -1, 38));

        sexo1.setBackground(new java.awt.Color(0, 0, 0));
        sexo1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sexo1.setForeground(new java.awt.Color(0, 0, 0));
        sexo1.setText("Sexo");
        sexo1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P4.add(sexo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, -1, -1));

        nomeField.setBackground(new java.awt.Color(255, 255, 255));
        nomeField.setForeground(new java.awt.Color(0, 0, 0));
        nomeField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        P4.add(nomeField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 170, 24));

        email1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        email1.setForeground(new java.awt.Color(0, 0, 0));
        email1.setText("Email");
        P4.add(email1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, -1, -1));

        emailField.setBackground(new java.awt.Color(255, 255, 255));
        emailField.setForeground(new java.awt.Color(0, 0, 0));
        emailField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                emailFieldKeyTyped(evt);
            }
        });
        P4.add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 170, 22));

        contacto1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        contacto1.setForeground(new java.awt.Color(0, 0, 0));
        contacto1.setText("Contacto");
        P4.add(contacto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, -1, -1));

        moradaField.setBackground(new java.awt.Color(255, 255, 255));
        moradaField.setForeground(new java.awt.Color(0, 0, 0));
        P4.add(moradaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 170, 22));

        Cadastrar1.setBackground(new java.awt.Color(51, 255, 51));
        Cadastrar1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        Cadastrar1.setForeground(new java.awt.Color(0, 0, 0));
        Cadastrar1.setText("Cadastrar");
        Cadastrar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cadastrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cadastrar1ActionPerformed(evt);
            }
        });
        P4.add(Cadastrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 555, -1, 30));

        P13.setBackground(new java.awt.Color(0, 153, 153));

        marca1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        marca1.setForeground(new java.awt.Color(0, 0, 0));
        marca1.setText("Marca");

        modelo1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        modelo1.setForeground(new java.awt.Color(0, 0, 0));
        modelo1.setText("Modelo");

        matricula1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        matricula1.setForeground(new java.awt.Color(0, 0, 0));
        matricula1.setText("Matricula");

        statusComboBox.setBackground(new java.awt.Color(255, 255, 255));
        statusComboBox.setForeground(new java.awt.Color(0, 0, 0));
        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Status");

        marcaField.setBackground(new java.awt.Color(255, 255, 255));
        marcaField.setForeground(new java.awt.Color(0, 0, 0));

        modeloField.setBackground(new java.awt.Color(255, 255, 255));
        modeloField.setForeground(new java.awt.Color(0, 0, 0));

        jTextFieldMatricula.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldMatricula.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldMatricula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldMatriculaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout P13Layout = new javax.swing.GroupLayout(P13);
        P13.setLayout(P13Layout);
        P13Layout.setHorizontalGroup(
            P13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P13Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(P13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modelo1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(marca1)
                    .addComponent(matricula1)
                    .addComponent(jLabel10))
                .addGap(37, 37, 37)
                .addGroup(P13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(marcaField)
                    .addComponent(statusComboBox, 0, 171, Short.MAX_VALUE)
                    .addComponent(modeloField)
                    .addComponent(jTextFieldMatricula))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        P13Layout.setVerticalGroup(
            P13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P13Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(P13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(marca1)
                    .addComponent(marcaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(P13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modelo1)
                    .addComponent(modeloField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(P13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matricula1)
                    .addComponent(jTextFieldMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(P13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        P4.add(P13, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 390, 290));

        morada1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        morada1.setForeground(new java.awt.Color(0, 0, 0));
        morada1.setText("Morada");
        P4.add(morada1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, -1, -1));

        contactoField.setBackground(new java.awt.Color(255, 255, 255));
        contactoField.setForeground(new java.awt.Color(0, 0, 0));
        contactoField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contactoFieldKeyTyped(evt);
            }
        });
        P4.add(contactoField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, 170, 20));

        nome1.setBackground(new java.awt.Color(0, 0, 0));
        nome1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nome1.setForeground(new java.awt.Color(0, 0, 0));
        nome1.setText("Nome");
        P4.add(nome1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, -1, -1));

        sexoComboBox.setBackground(new java.awt.Color(255, 255, 255));
        sexoComboBox.setForeground(new java.awt.Color(0, 0, 0));
        sexoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));
        sexoComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P4.add(sexoComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 170, -1));

        P14.setBackground(new java.awt.Color(0, 204, 204));

        jButton12.setBackground(new java.awt.Color(51, 255, 204));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton12.setForeground(new java.awt.Color(0, 0, 0));
        jButton12.setText("Salvar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout P14Layout = new javax.swing.GroupLayout(P14);
        P14.setLayout(P14Layout);
        P14Layout.setHorizontalGroup(
            P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P14Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        P14Layout.setVerticalGroup(
            P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, P14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        P4.add(P14, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 550, 130, 40));

        Paineis.addTab("tab6", P4);

        P15.setBackground(new java.awt.Color(204, 255, 255));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Gestao de Productos");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Produto");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Data de Validade");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Categoria");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Quantidade");

        nomeProdutoField.setBackground(new java.awt.Color(255, 255, 255));
        nomeProdutoField.setForeground(new java.awt.Color(0, 0, 0));

        dataValidadeField.setBackground(new java.awt.Color(255, 255, 255));
        dataValidadeField.setForeground(new java.awt.Color(0, 0, 0));

        categoriaComboBox.setBackground(new java.awt.Color(255, 255, 255));
        categoriaComboBox.setForeground(new java.awt.Color(0, 0, 0));
        categoriaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Produtos de Limpeza", "Peças", "Acessórios", "Equipamento", "" }));

        AdicionarProduto.setBackground(new java.awt.Color(51, 153, 255));
        AdicionarProduto.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        AdicionarProduto.setForeground(new java.awt.Color(0, 0, 0));
        AdicionarProduto.setText("Adicionar");
        AdicionarProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AdicionarProdutoMouseClicked(evt);
            }
        });
        AdicionarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdicionarProdutoActionPerformed(evt);
            }
        });

        validadeTextArea.setBackground(new java.awt.Color(204, 204, 255));
        validadeTextArea.setColumns(20);
        validadeTextArea.setForeground(new java.awt.Color(0, 0, 0));
        validadeTextArea.setRows(5);
        jScrollPane7.setViewportView(validadeTextArea);

        javax.swing.GroupLayout P15Layout = new javax.swing.GroupLayout(P15);
        P15.setLayout(P15Layout);
        P15Layout.setHorizontalGroup(
            P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P15Layout.createSequentialGroup()
                .addGroup(P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(P15Layout.createSequentialGroup()
                        .addGap(468, 468, 468)
                        .addComponent(jLabel7))
                    .addGroup(P15Layout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addGroup(P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29))
                        .addGap(23, 23, 23)
                        .addGroup(P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nomeProdutoField)
                            .addComponent(dataValidadeField)
                            .addComponent(categoriaComboBox, 0, 173, Short.MAX_VALUE)
                            .addComponent(quantidadeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(192, 192, 192)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(P15Layout.createSequentialGroup()
                        .addGap(495, 495, 495)
                        .addComponent(AdicionarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(2418, Short.MAX_VALUE))
        );
        P15Layout.setVerticalGroup(
            P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P15Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel7)
                .addGroup(P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(P15Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(nomeProdutoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(dataValidadeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(categoriaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(quantidadeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(P15Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addComponent(AdicionarProduto)
                .addContainerGap(157, Short.MAX_VALUE))
        );

        Paineis.addTab("tab2", P15);

        P2.setBackground(new java.awt.Color(153, 255, 255));

        feedbackTable.setBackground(new java.awt.Color(255, 255, 255));
        feedbackTable.setForeground(new java.awt.Color(0, 0, 0));
        feedbackTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cliente", "Data", "Comentario"
            }
        ));
        feedbackTable.setRowHeight(25);
        feedbackTable.setSelectionBackground(new java.awt.Color(184, 207, 229));
        jScrollPane4.setViewportView(feedbackTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(230, 240, 250));
        jPanel5.setAutoscrolls(true);

        comentarioArea.setEditable(false);
        comentarioArea.setBackground(new java.awt.Color(255, 255, 240));
        comentarioArea.setColumns(20);
        comentarioArea.setForeground(new java.awt.Color(0, 0, 0));
        comentarioArea.setLineWrap(true);
        comentarioArea.setRows(5);
        comentarioArea.setWrapStyleWord(true);
        comentarioArea.setMinimumSize(new java.awt.Dimension(10, 20));
        jScrollPane5.setViewportView(comentarioArea);

        viewDetailsButton.setBackground(new java.awt.Color(100, 149, 237));
        viewDetailsButton.setForeground(new java.awt.Color(255, 255, 255));
        viewDetailsButton.setText("Atualizar Detalhes");
        viewDetailsButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        viewDetailsButton.setFocusPainted(false);
        viewDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDetailsButtonActionPerformed(evt);
            }
        });

        clienteLabel.setForeground(new java.awt.Color(0, 0, 0));
        clienteLabel.setText("Cliente");

        dataLabel.setForeground(new java.awt.Color(0, 0, 0));
        dataLabel.setText("Data");

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Comentario:");

        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Nome:");

        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Data:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(viewDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dataLabel)
                            .addComponent(clienteLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(clienteLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("FeedBack dos Clientes");

        javax.swing.GroupLayout P2Layout = new javax.swing.GroupLayout(P2);
        P2.setLayout(P2Layout);
        P2Layout.setHorizontalGroup(
            P2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P2Layout.createSequentialGroup()
                .addGroup(P2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(P2Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, P2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)
                        .addGap(106, 106, 106)))
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(212, 212, 212)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2004, Short.MAX_VALUE))
        );
        P2Layout.setVerticalGroup(
            P2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P2Layout.createSequentialGroup()
                .addGroup(P2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(P2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(P2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
            .addGroup(P2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Paineis.addTab("tab13", P2);

        P16.setBackground(new java.awt.Color(0, 153, 153));

        jLabel11.setFont(new java.awt.Font("Monotype Corsiva", 0, 54)); // NOI18N
        jLabel11.setText("Promoção");

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Adicionar Promoção");

        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Desconto(%)");

        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Validade (dd/mm/aaaa)");

        promocaoField.setBackground(new java.awt.Color(255, 255, 255));
        promocaoField.setForeground(new java.awt.Color(0, 0, 0));

        descontoField.setBackground(new java.awt.Color(255, 255, 255));
        descontoField.setForeground(new java.awt.Color(0, 0, 0));

        validadeField.setBackground(new java.awt.Color(255, 255, 255));
        validadeField.setForeground(new java.awt.Color(0, 0, 0));

        adicionar.setBackground(new java.awt.Color(102, 255, 102));
        adicionar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        adicionar.setForeground(new java.awt.Color(0, 0, 0));
        adicionar.setText("Adicionar");
        adicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarActionPerformed(evt);
            }
        });

        tabelaPromocoes.setBackground(new java.awt.Color(204, 204, 255));
        tabelaPromocoes.setForeground(new java.awt.Color(0, 0, 0));
        tabelaPromocoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Promocao", "Desconto (%)", "Validade"
            }
        ));
        tabelaPromocoes.setSelectionBackground(new java.awt.Color(51, 255, 102));
        tabelaPromocoes.setShowGrid(false);
        jScrollPane6.setViewportView(tabelaPromocoes);

        editar.setText("Editar Promoção");
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });

        excluir.setText("Excluir Promoção");
        excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excluirActionPerformed(evt);
            }
        });

        editar1.setBackground(new java.awt.Color(0, 153, 255));
        editar1.setForeground(new java.awt.Color(0, 0, 0));
        editar1.setText("Editar Promoção");
        editar1.setActionCommand("Editar ");
        editar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editar1ActionPerformed(evt);
            }
        });

        excluir1.setBackground(new java.awt.Color(255, 51, 51));
        excluir1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        excluir1.setForeground(new java.awt.Color(0, 0, 0));
        excluir1.setText("Excluir Promoção");
        excluir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excluir1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout P16Layout = new javax.swing.GroupLayout(P16);
        P16.setLayout(P16Layout);
        P16Layout.setHorizontalGroup(
            P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P16Layout.createSequentialGroup()
                .addGroup(P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(P16Layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addGroup(P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(P16Layout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(adicionar)
                                .addGap(57, 57, 57)
                                .addComponent(editar1)
                                .addGap(47, 47, 47)
                                .addComponent(excluir1))
                            .addGroup(P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, P16Layout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(descontoField, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, P16Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(65, 65, 65)
                                        .addComponent(promocaoField, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(P16Layout.createSequentialGroup()
                                    .addComponent(jLabel33)
                                    .addGap(65, 65, 65)
                                    .addComponent(validadeField, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(P16Layout.createSequentialGroup()
                        .addGap(258, 258, 258)
                        .addComponent(editar)
                        .addGap(157, 157, 157)
                        .addComponent(excluir))
                    .addGroup(P16Layout.createSequentialGroup()
                        .addGap(567, 567, 567)
                        .addComponent(jLabel11))
                    .addGroup(P16Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1089, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(2349, Short.MAX_VALUE))
        );
        P16Layout.setVerticalGroup(
            P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P16Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel11)
                .addGap(85, 85, 85)
                .addGroup(P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(promocaoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(35, 35, 35)
                .addGroup(P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descontoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(30, 30, 30)
                .addGroup(P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(validadeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(71, 71, 71)
                .addGroup(P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adicionar)
                    .addComponent(editar1)
                    .addComponent(excluir1))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addGroup(P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editar)
                    .addComponent(excluir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Paineis.addTab("tab2", P16);

        P17.setBackground(new java.awt.Color(0, 153, 153));
        P17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableAgendamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "marca", "placa", "proprietario", "contacto", "tipo_viatura", "hora_entrada", "hora_saida", "total_servicos"
            }
        ));
        jScrollPane8.setViewportView(jTableAgendamentos);

        P17.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 1090, -1));

        jButton6.setBackground(new java.awt.Color(51, 255, 51));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 0, 0));
        jButton6.setText("Editar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        P17.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 600, -1, -1));

        jButtonSalvarEdicao.setBackground(new java.awt.Color(0, 204, 204));
        jButtonSalvarEdicao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSalvarEdicao.setForeground(new java.awt.Color(0, 0, 0));
        jButtonSalvarEdicao.setText("Salvar");
        jButtonSalvarEdicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarEdicaoActionPerformed(evt);
            }
        });
        P17.add(jButtonSalvarEdicao, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 600, -1, -1));

        jButtonRemover1.setBackground(new java.awt.Color(255, 51, 51));
        jButtonRemover1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonRemover1.setForeground(new java.awt.Color(0, 0, 0));
        jButtonRemover1.setText("Remover");
        jButtonRemover1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemover1ActionPerformed(evt);
            }
        });
        P17.add(jButtonRemover1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 600, -1, -1));

        Paineis.addTab("tab8", P17);

        getContentPane().add(Paineis, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, -30, 3470, 760));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P3);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        int selectedRow = clienteTable.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = clienteTable.convertRowIndexToModel(selectedRow);

        // Ajuste: Pega o ID da coluna 0 e o nome da coluna correta (assumindo coluna 1)
        String nome = (String) clienteTable.getValueAt(modelRow, 1); // Pega o nome da coluna 1
        String sexo = (String) clienteTable.getValueAt(modelRow, 2); // Ajuste para a coluna correta
        String email = (String) clienteTable.getValueAt(modelRow, 3);
        String contacto = (String) clienteTable.getValueAt(modelRow, 4);
        String morada = (String) clienteTable.getValueAt(modelRow, 5);
        String marca = (String) clienteTable.getValueAt(modelRow, 6);
        String modelo = (String) clienteTable.getValueAt(modelRow, 7);
        String matricula = (String) clienteTable.getValueAt(modelRow, 8);
        String status = (String) clienteTable.getValueAt(modelRow, 9);

        // Configura os dados no painel de cadastro
        setClienteData(nome, sexo, email, contacto, morada, marca, modelo, matricula, status);

        // Muda para o painel de cadastro (P2)
        Paineis.setSelectedComponent(P4);
         P14.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecione um cliente para editar.");
        }
        }

        // Método para configurar os dados do cliente no painel de cadastro
        private void setClienteData(String nome, String sexo, String email, String contacto, String morada, String marca, String modelo, String matricula, String status) {
            // Configura os campos no painel de cadastro, verificando se os valores não são nulos
            nomeField.setText(nome != null ? nome : "");
            sexoComboBox.setSelectedItem(sexo != null ? sexo : "");
            emailField.setText(email != null ? email : "");
            contactoField.setText(contacto != null ? contacto : "");
            moradaField.setText(morada != null ? morada : "");
            marcaField.setText(marca != null ? marca : "");
            modeloField.setText(modelo != null ? modelo : "");
            jTextFieldMatricula.setText(matricula != null ? matricula : "");
            statusComboBox.setSelectedItem(status != null ? status : "");

    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
int selectedRow = clienteTable.getSelectedRow(); // Use o nome correto da tabela
    if (selectedRow != -1) {
        int modelRow = clienteTable.convertRowIndexToModel(selectedRow);
        
        // Obter o ID e o nome do cliente selecionado
        int clienteId = (int) clienteTable.getValueAt(modelRow, 0); // Coluna do ID
        String nome = (String) clienteTable.getValueAt(modelRow, 1); // Coluna do nome
        
        // Confirmar a exclusão
        int confirmation = JOptionPane.showConfirmDialog(null, 
                "Tem certeza que deseja excluir " + nome + "?", 
                "Confirmar Exclusão", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                // Configurar a conexão com o banco de dados
                String url = "jdbc:mysql://localhost:3306/testesdb";
                String usuario = "root";
                String senha = "";

                try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
                    // Atualizar o status do cliente para "Inativo" no banco de dados
                    String sql = "UPDATE clientes SET status = 'Inativo' WHERE id = ?";
                    try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
                        preparedStatement.setInt(1, clienteId);
                        
                        // Executar a atualização
                        int linhasAfetadas = preparedStatement.executeUpdate();
                        if (linhasAfetadas > 0) {
                            // Atualizar a tabela com o novo status
                            DefaultTableModel tableModel = (DefaultTableModel) clienteTable.getModel();
                            tableModel.setValueAt("Inativo", modelRow, 9); // Atualiza a coluna de status
                            
                            // Mostrar mensagem de confirmação
                            JOptionPane.showMessageDialog(null, nome + " foi marcado como inativo.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Falha ao atualizar o status no banco de dados.", 
                                                          "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados.", 
                                              "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    } else {
        // Mensagem se nenhuma linha foi selecionada
        JOptionPane.showMessageDialog(null, "Por favor, selecione um cliente para excluir.");
    }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void pesquisajTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisajTextFieldActionPerformed
        // Função para pesquisa na tabela de clientes
        String pesquisa = pesquisajTextField.getText().trim().toLowerCase(); // Obter texto da pesquisa
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) clienteTable.getModel());
        clienteTable.setRowSorter(sorter);

        try {
            if (pesquisa.isEmpty()) {
                sorter.setRowFilter(null); // Mostra todas as linhas se o campo de pesquisa estiver vazio
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + pesquisa)); // Aplica o filtro baseado na pesquisa, ignorando maiúsculas/minúsculas
            }
        } catch (PatternSyntaxException e) {
            JOptionPane.showMessageDialog(null, "Formato de pesquisa inválido.", "Erro de Pesquisa", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_pesquisajTextFieldActionPerformed

    private void jTextFieldPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPlacaActionPerformed
        // Chamar o método para buscar os dados do cliente com base na matrícula
        buscarDadosClientePorMatricula();
        }

        // Método para buscar os dados do cliente com base na matrícula
        private void buscarDadosClientePorMatricula() {
            // Obter a matrícula inserida no JTextField
            String matricula = jTextFieldPlaca.getText().trim();

            // Verificar se a matrícula foi informada
            if (matricula.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, insira a matrícula para buscar os dados do cliente.", "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Connection conexao = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultado = null;

            try {
                // Configurar a conexão com o banco de dados
                String url = "jdbc:mysql://localhost:3306/testesdb";
                String usuario = "root";
                String senha = "";

                conexao = DriverManager.getConnection(url, usuario, senha);

                // SQL para buscar os dados do cliente com base na matrícula
                String sql = "SELECT nome, marca, contacto FROM clientes WHERE matricula = ?";
                preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setString(1, matricula);

                // Executar a consulta
                resultado = preparedStatement.executeQuery();

                // Verificar se o cliente foi encontrado
                if (resultado.next()) {
                    // Obter os dados do cliente
                    String nome = resultado.getString("nome");
                    String marca = resultado.getString("marca");
                    String contacto = resultado.getString("contacto");

                    // Preencher os campos na tela de agendamento (P2)
                    jTextFieldProprietario.setText(nome);
                    jTextFieldMarca.setText(marca);
                    jTextFieldContacto.setText(contacto);
                } else {
                    // Exibir mensagem caso a matrícula não seja encontrada
                    JOptionPane.showMessageDialog(null, "Cliente não encontrado para a matrícula fornecida.", "Cliente não encontrado", JOptionPane.WARNING_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } finally {
                // Fechar os recursos
                try {
                    if (resultado != null) resultado.close();
                    if (preparedStatement != null) preparedStatement.close();
                    if (conexao != null) conexao.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar os recursos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }

    }//GEN-LAST:event_jTextFieldPlacaActionPerformed

    private void jButtonSalvarAgendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarAgendamentoActionPerformed
        // TODO add your handling code here:

        // Obtenha os valores dos campos
        String marca = jTextFieldMarca.getText();
        String placa = jTextFieldPlaca.getText();
        String proprietario = jTextFieldProprietario.getText();
        String contacto = jTextFieldContacto.getText();
        String tipoViatura = jComboBoxTipoViatura.getSelectedItem().toString();
        String horaEntrada = jTextFieldHoraEntrada.getText();
        String horaSaida = jTextFieldHoraSaida.getText();

        // Verifique se todos os campos foram preenchidos corretamente
        if (marca.isEmpty() || placa.isEmpty() || proprietario.isEmpty() || contacto.isEmpty() || tipoViatura.isEmpty() || horaEntrada.isEmpty() || horaSaida.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }

        // Tente converter o campo "total" para um número
        double total = 0;
        try {
            total = Double.parseDouble(jTextFieldTotal.getText());

            if (total <= 0) {
                JOptionPane.showMessageDialog(

                    this, "Selecione pelo menos um serviço.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro no valor total. Verifique se o valor está correto.");
            return;
        }

        // Inserção no banco de dados
        try {
            java.sql.Connection con = Conexao.connect();
            String sql = "INSERT INTO agendamentos (marca, placa, proprietario, contacto, tipo_viatura, hora_entrada, hora_saida, total_servicos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, marca);
            pst.setString(2, placa);
            pst.setString(3, proprietario);
            pst.setString(4, contacto);
            pst.setString(5, tipoViatura);
            pst.setString(6, horaEntrada);
            pst.setString(7, horaSaida);
            pst.setDouble(8, total);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Agendamento salvo com sucesso!");

            pst.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar agendamento: " + e.getMessage());
        }
    }//GEN-LAST:event_jButtonSalvarAgendamentoActionPerformed

    private void emailFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailFieldKeyTyped
        // Obter o texto atual do campo de e-mail
        String email = emailField.getText().trim();

        // Expressão regular para validar o formato do e-mail
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        // Verificar se o e-mail corresponde ao padrão
        if (!email.matches(emailPattern)) {
            // Exibir mensagem de erro se o formato estiver incorreto
            emailField.setForeground(java.awt.Color.RED);
            emailField.setToolTipText("Email inválido. Formato esperado: example@gmail.com");
        } else {
            // Se o formato estiver correto, resetar a cor para a padrão
            emailField.setForeground(java.awt.Color.BLACK);
            emailField.setToolTipText(null);
        }

    }//GEN-LAST:event_emailFieldKeyTyped

    private void Cadastrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cadastrar1ActionPerformed

        // Código para o cadastro do cliente, que inclui carregar os dados novamente após a inserção
        // Obter os dados dos campos
        String nome = nomeField.getText().trim();
        String sexo = (String) sexoComboBox.getSelectedItem();
        String email = emailField.getText().trim();
        String morada = moradaField.getText().trim();
        String contacto = contactoField.getText().trim();
        String marca = marcaField.getText().trim();
        String modelo = modeloField.getText().trim();
        String matricula = jTextFieldMatricula.getText().trim();
        String status = statusComboBox.getSelectedItem().toString().toLowerCase(); // Converte para minúsculo

        // Validação dos campos obrigatórios
        if (nome.isEmpty() || sexo == null || email.isEmpty() || morada.isEmpty() || contacto.isEmpty() ||
            marca.isEmpty() || modelo.isEmpty() || matricula.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.",
                "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação do formato da matrícula
        if (!matricula.matches("[A-Z]{3}-\\d{3}-[A-Z]{2}")) {
            JOptionPane.showMessageDialog(null, "Placa inválida. Formato esperado: ABC-123-XY.",
                "Formato inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar se o status é válido
        if (!status.equals("ativo") && !status.equals("inativo")) {
            JOptionPane.showMessageDialog(null, "Status inválido. Os valores permitidos são 'ativo' ou 'inativo'.",
                "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Configurar a conexão com o banco de dados
            String url = "jdbc:mysql://localhost:3306/testesdb";
            String usuario = "root";
            String senha = "";

            try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {

                // Verificar se o cliente já está cadastrado com base no email, contacto ou matrícula
                String sqlVerificacao = "SELECT COUNT(*) FROM clientes WHERE email = ? OR contacto = ? OR matricula = ?";
                try (PreparedStatement preparedStatementVerificacao = conexao.prepareStatement(sqlVerificacao)) {
                    preparedStatementVerificacao.setString(1, email);
                    preparedStatementVerificacao.setString(2, contacto);
                    preparedStatementVerificacao.setString(3, matricula);
                    try (ResultSet resultado = preparedStatementVerificacao.executeQuery()) {
                        resultado.next();
                        int existeCliente = resultado.getInt(1);

                        if (existeCliente > 0) {
                            JOptionPane.showMessageDialog(null, "Cliente já cadastrado com este email, contacto ou matrícula.",
                                "Cadastro duplicado", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }

                // SQL para inserir o registro
                String sql = "INSERT INTO clientes (nome, sexo, email, contacto, morada, marca, modelo, matricula, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
                    preparedStatement.setString(1, nome);
                    preparedStatement.setString(2, sexo);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, contacto);
                    preparedStatement.setString(5, morada);
                    preparedStatement.setString(6, marca);
                    preparedStatement.setString(7, modelo);
                    preparedStatement.setString(8, matricula);
                    preparedStatement.setString(9, status);

                    // Executar a inserção
                    int linhasAfetadas = preparedStatement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                        // Atualizar a tabela para mostrar todos os clientes, incluindo o novo
                        carregarClientesNaTabela();

                        // Limpar os campos de entrada
                        nomeField.setText("");
                        sexoComboBox.setSelectedIndex(0);
                        emailField.setText("");
                        contactoField.setText("");
                        moradaField.setText("");
                        marcaField.setText("");
                        modeloField.setText("");
                        jTextFieldMatricula.setText("");
                        statusComboBox.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "Falha ao realizar o cadastro.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_Cadastrar1ActionPerformed

    private void jTextFieldMatriculaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMatriculaKeyTyped
        // Obter o texto atual no campo de matrícula
        String matricula = jTextFieldMatricula.getText();

        // Verificar o comprimento máximo permitido
        if (matricula.length() >= 10) { // 10 é o comprimento máximo de "ABC-123-XY"
            evt.consume(); // Cancelar o evento se o comprimento máximo for atingido
            JOptionPane.showMessageDialog(null, "Limite de caracteres atingido. O formato esperado é ABC-123-XY.",
                "Validação de Matrícula", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar o formato da matrícula
        if (!matricula.matches("[A-Z]{3}-\\d{3}-[A-Z]{2}")) {
            jTextFieldMatricula.setForeground(java.awt.Color.BLACK); // Mudar a cor do texto para vermelho se não corresponder
            // Opcional: você pode exibir uma mensagem aqui também
        } else {
            jTextFieldMatricula.setForeground(java.awt.Color.RED); // Mudar a cor do texto para preto se corresponder
        }
    }//GEN-LAST:event_jTextFieldMatriculaKeyTyped

    private void contactoFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactoFieldKeyTyped
        // Obter o texto atual no campo de contato
        String contacto = contactoField.getText();

        // Verificar o comprimento máximo permitido (por exemplo, 9 dígitos)
        if (contacto.length() >= 9) { // Supondo que o limite é 9 dígitos
            evt.consume(); // Cancelar o evento se o comprimento máximo for atingido
            JOptionPane.showMessageDialog(null, "Limite de caracteres atingido. O número de contato deve ter 9 dígitos.",
                "Validação de Contato", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Se o comprimento do texto atual for 1 ou 2, permitir qualquer dígito
        if (contacto.length() < 2) {
            return; // Permitir que o usuário digite os primeiros dois dígitos
        }

        // Validar os primeiros dois dígitos
        String prefixo = contacto.substring(0, 2);
        if (contacto.length() == 2 && !prefixo.matches("82|83|84|85|86|87")) {
            contactoField.setForeground(java.awt.Color.RED); // Mudar a cor do texto para vermelho se não corresponder
            evt.consume(); // Cancelar o evento se os dois primeiros dígitos não forem válidos
            JOptionPane.showMessageDialog(null, "Os dois primeiros dígitos devem ser 82, 83, 84, 85, 86 ou 87.",
                "Validação de Contato", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Se a validação passar, você pode definir a cor do texto para indicar que está tudo certo
        contactoField.setForeground(java.awt.Color.BLACK); // Mudar a cor do texto para preto

    }//GEN-LAST:event_contactoFieldKeyTyped

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int selectedRow = clienteTable.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = clienteTable.convertRowIndexToModel(selectedRow);

            // Obter o ID do cliente selecionado
            int clienteId = (int) clienteTable.getValueAt(modelRow, 0);

            // Obter os dados dos campos de entrada
            String nome = nomeField.getText().trim();
            String sexo = (String) sexoComboBox.getSelectedItem();
            String email = emailField.getText().trim();
            String contacto = contactoField.getText().trim();
            String morada = moradaField.getText().trim();
            String marca = marcaField.getText().trim();
            String modelo = modeloField.getText().trim();
            String matricula = jTextFieldMatricula.getText().trim();
            String status = statusComboBox.getSelectedItem().toString().toLowerCase();

            // Validação dos campos obrigatórios
            if (nome.isEmpty() || sexo == null || email.isEmpty() || morada.isEmpty() || contacto.isEmpty() ||
                marca.isEmpty() || modelo.isEmpty() || matricula.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.",
                    "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validação do formato da matrícula
            if (!matricula.matches("[A-Z]{3}-\\d{3}-[A-Z]{2}")) {
                JOptionPane.showMessageDialog(null, "Placa inválida. Formato esperado: ABC-123-XY.",
                    "Formato inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar se o status é válido
            if (!status.equals("ativo") && !status.equals("inativo")) {
                JOptionPane.showMessageDialog(null, "Status inválido. Os valores permitidos são 'ativo' ou 'inativo'.",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Configurar a conexão com o banco de dados
                String url = "jdbc:mysql://localhost:3306/testesdb";
                String usuario = "root";
                String senha = "";

                try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
                    // SQL para atualizar o registro do cliente
                    String sql = "UPDATE clientes SET nome = ?, sexo = ?, email = ?, contacto = ?, morada = ?, " +
                    "marca = ?, modelo = ?, matricula = ?, status = ? WHERE id = ?";

                    try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
                        preparedStatement.setString(1, nome);
                        preparedStatement.setString(2, sexo);
                        preparedStatement.setString(3, email);
                        preparedStatement.setString(4, contacto);
                        preparedStatement.setString(5, morada);
                        preparedStatement.setString(6, marca);
                        preparedStatement.setString(7, modelo);
                        preparedStatement.setString(8, matricula);
                        preparedStatement.setString(9, status);
                        preparedStatement.setInt(10, clienteId);

                        // Executar a atualização
                        int linhasAfetadas = preparedStatement.executeUpdate();
                        if (linhasAfetadas > 0) {
                            JOptionPane.showMessageDialog(null, "Alterações salvas com sucesso!",
                                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                            // Atualizar a tabela com os novos dados
                            clienteTable.setValueAt(nome, modelRow, 1);
                            clienteTable.setValueAt(sexo, modelRow, 2);
                            clienteTable.setValueAt(email, modelRow, 3);
                            clienteTable.setValueAt(contacto, modelRow, 4);
                            clienteTable.setValueAt(morada, modelRow, 5);
                            clienteTable.setValueAt(marca, modelRow, 6);
                            clienteTable.setValueAt(modelo, modelRow, 7);
                            clienteTable.setValueAt(matricula, modelRow, 8);
                            clienteTable.setValueAt(status, modelRow, 9);

                            // Limpar os campos de entrada
                            nomeField.setText("");
                            sexoComboBox.setSelectedIndex(0);
                            emailField.setText("");
                            contactoField.setText("");
                            moradaField.setText("");
                            marcaField.setText("");
                            modeloField.setText("");
                            jTextFieldMatricula.setText("");
                            statusComboBox.setSelectedIndex(0);
                        } else {
                            JOptionPane.showMessageDialog(null, "Falha ao salvar as alterações.",
                                "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecione um cliente para salvar as alterações.");
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void CadastrarclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CadastrarclienteMouseClicked
        // TODO add your handling code here:
        Paineis.setSelectedComponent(P4);
        P14.setVisible(false);
    }//GEN-LAST:event_CadastrarclienteMouseClicked

    private void feedbackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feedbackMouseClicked
        Paineis.setSelectedComponent(P2);
    }//GEN-LAST:event_feedbackMouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        Paineis.setSelectedComponent(P16);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void relatorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relatorioMouseClicked
        Paineis.setSelectedComponent(P5);
    }//GEN-LAST:event_relatorioMouseClicked

    private void StockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StockMouseClicked
        Paineis.setSelectedComponent(P15);
    }//GEN-LAST:event_StockMouseClicked

    private void AgendamentosPendentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendamentosPendentesMouseClicked
        Paineis.setSelectedComponent(P17);
    }//GEN-LAST:event_AgendamentosPendentesMouseClicked

    private void AgendamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendamentosMouseClicked
        Paineis.setSelectedComponent(P7);
    }//GEN-LAST:event_AgendamentosMouseClicked

    private void Ver_dadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Ver_dadosMouseClicked
        Paineis.setSelectedComponent(P10);
    }//GEN-LAST:event_Ver_dadosMouseClicked

    private void AdicionarProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdicionarProdutoMouseClicked

    }//GEN-LAST:event_AdicionarProdutoMouseClicked

    private void AdicionarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdicionarProdutoActionPerformed
        // Método para adicionar o produto ao banco de dados
        String nomeProduto = nomeProdutoField.getText(); // Campo para o nome do produto
        String dataValidadeStr = dataValidadeField.getText(); // Campo para a data de validade
        String categoria = (String) categoriaComboBox.getSelectedItem(); // Obtém a categoria selecionada
        int quantidade = (int) quantidadeSpinner.getValue(); // Obtenção do valor do JSpinner

        // Formatação da data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataValidade;
        try {
            dataValidade = LocalDate.parse(dataValidadeStr, formatter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data de validade inválida. Use o formato yyyy-MM-dd.");
            return;
        }

        // Calculando o período entre a data atual e a data de validade
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataAtual, dataValidade);

        // Gerando a mensagem de validade
        String mensagemValidade;
        if (periodo.isNegative()) {
            mensagemValidade = "O produto já está fora do prazo.";
            validadeTextArea.setText(mensagemValidade);
            JOptionPane.showMessageDialog(null, mensagemValidade);
            return; // Interrompe a execução para não adicionar o produto
        } else {
            int anos = periodo.getYears();
            int meses = periodo.getMonths();
            int dias = periodo.getDays();

            if (anos > 0) {
                mensagemValidade = String.format("O produto está dentro do prazo (%d ano(s), %d mes(es) e %d dia(s) restantes).", anos, meses, dias);
            } else if (meses > 0) {
                mensagemValidade = String.format("O produto está dentro do prazo (%d mes(es) e %d dia(s) restantes).", meses, dias);
            } else {
                mensagemValidade = String.format("O produto está dentro do prazo (%d dia(s) restantes).", dias);
            }
        }

        // Atualizando o ValidadeTextArea com a mensagem
        validadeTextArea.setText(mensagemValidade);

        // Conexão com o banco de dados
        String url = "jdbc:mysql://localhost:3306/testesdb";
        String usuario = "root";
        String senha = "";

        // Query de inserção
        String sql = "INSERT INTO produtos (nome, data_validade, categoria, quantidade) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            // Estabelecendo a conexão
            conn = DriverManager.getConnection(url, usuario, senha);
            preparedStatement = conn.prepareStatement(sql);

            // Configura os parâmetros da query
            preparedStatement.setString(1, nomeProduto);
            preparedStatement.setString(2, dataValidadeStr);
            preparedStatement.setString(3, categoria);
            preparedStatement.setInt(4, quantidade);

            // Executa o comando de inserção
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!");

                limparCampos(); // Limpa os campos após a adição
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar o produto: " + e.getMessage());
        } finally {
            // Fechando os recursos
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_AdicionarProdutoActionPerformed

    private void viewDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDetailsButtonActionPerformed

        // Obtém a linha selecionada na tabela
        int selectedRow = feedbackTable.getSelectedRow();

        // Verifica se uma linha foi selecionada
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um comentário na tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtém os valores da linha selecionada
        String cliente = feedbackTable.getValueAt(selectedRow, 0).toString(); // Nome do cliente na primeira coluna
        java.sql.Date data = (java.sql.Date) feedbackTable.getValueAt(selectedRow, 1); // Data na segunda coluna
        String comentario = feedbackTable.getValueAt(selectedRow, 2).toString(); // Comentário na terceira coluna

        // Atualiza as labels e o text area com os detalhes do feedback
        clienteLabel.setText(cliente); // Atualiza a label do cliente
        dataLabel.setText(data.toString()); // Atualiza a label da data
        comentarioArea.setText(comentario); // Atualiza o text area com o comentário

    }//GEN-LAST:event_viewDetailsButtonActionPerformed

    private void adicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarActionPerformed
    String nomePromocao = promocaoField.getText().trim();
    String descontoStr = descontoField.getText().trim();
    String validadeStr = validadeField.getText().trim();

    // Verifica se algum campo está vazio
    if (nomePromocao.isEmpty() || descontoStr.isEmpty() || validadeStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!");
        return;
    }

    // Verifica se o desconto está em formato numérico e se está entre 0 e 100
    double desconto;
    try {
        desconto = Double.parseDouble(descontoStr);
        if (desconto < 0 || desconto > 100) {
            JOptionPane.showMessageDialog(this, "O desconto deve estar entre 0 e 100%.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Desconto inválido! Por favor, insira um valor numérico.");
        return;
    }

    // Verifica se a validade está no formato correto (yyyy-MM-dd) e é uma data futura
    LocalDate validade;
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        validade = LocalDate.parse(validadeStr, formatter);
        if (validade.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "A validade deve ser uma data futura.");
            return;
        }
    } catch (DateTimeParseException e) {
        JOptionPane.showMessageDialog(this, "Data de validade inválida! Use o formato yyyy-MM-dd.");
        return;
    }

    // Inserção da promoção no banco de dados
    String url = "jdbc:mysql://localhost:3306/testesdb";  // Ajuste conforme seu ambiente
    String user = "root";
    String password = "";

    String query = "INSERT INTO promocoes_carwash (promocao, desconto, validade) VALUES (?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, nomePromocao);  // Nome da promoção
        pstmt.setDouble(2, desconto);      // Desconto
        pstmt.setDate(3, java.sql.Date.valueOf(validade));  // Validade (convertido para java.sql.Date)

        int rowsInserted = pstmt.executeUpdate();  // Executa a inserção

        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Promoção adicionada com sucesso ao banco de dados!");
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao adicionar promoção.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao conectar com o banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
    }

    // Limpar os campos após a inserção
    limparCampos();
}

// Método para limpar os campos do formulário
private void limparCampos() {
    promocaoField.setText("");
    descontoField.setText("");
    validadeField.setText("");

    }//GEN-LAST:event_adicionarActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed

    }//GEN-LAST:event_editarActionPerformed

    private void excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excluirActionPerformed

    }//GEN-LAST:event_excluirActionPerformed

    private void editar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editar1ActionPerformed
        // Supondo que haja uma tabela de promoções para selecionar e editar
        int selectedRow = tabelaPromocoes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma promoção para editar.");
            return;
        }

        String nomePromocao = promocaoField.getText().trim();
        String descontoStr = descontoField.getText().trim();
        String validadeStr = validadeField.getText().trim();

        if (nomePromocao.isEmpty() || descontoStr.isEmpty() || validadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos para editar.");
            return;
        }

        // Lógica para atualizar a promoção na tabela (exemplo)
        tabelaPromocoes.setValueAt(nomePromocao, selectedRow, 0);
        tabelaPromocoes.setValueAt(descontoStr, selectedRow, 1);
        tabelaPromocoes.setValueAt(validadeStr, selectedRow, 2);

        JOptionPane.showMessageDialog(this, "Promoção editada com sucesso!");
        limparCampos();
    }//GEN-LAST:event_editar1ActionPerformed

    private void excluir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excluir1ActionPerformed
        int selectedRow = tabelaPromocoes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma promoção para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esta promoção?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Lógica para remover a promoção da tabela (exemplo)
            ((javax.swing.table.DefaultTableModel) tabelaPromocoes.getModel()).removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Promoção excluída com sucesso!");
            limparCampos();
        }
    }//GEN-LAST:event_excluir1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTableAgendamentos.getSelectedRow(); // Verifica se há uma linha selecionada

        if (selectedRow != -1) { // Se uma linha foi selecionada
            // Preencher os campos com os valores da linha selecionada
            jTextFieldMarca.setText(jTableAgendamentos.getValueAt(selectedRow, 0).toString());
            jTextFieldPlaca.setText(jTableAgendamentos.getValueAt(selectedRow, 1).toString());
            jTextFieldProprietario.setText(jTableAgendamentos.getValueAt(selectedRow, 2).toString());
            moradaField.setText(jTableAgendamentos.getValueAt(selectedRow, 3).toString());
            jComboBoxTipoViatura.setSelectedItem(jTableAgendamentos.getValueAt(selectedRow, 4).toString());
            jTextFieldHoraEntrada.setText(jTableAgendamentos.getValueAt(selectedRow, 5).toString());
            jTextFieldHoraSaida.setText(jTableAgendamentos.getValueAt(selectedRow, 6).toString());
            jTextFieldTotal.setText(jTableAgendamentos.getValueAt(selectedRow, 7).toString());

            // Ativa o botão "Salvar" para permitir salvar as edições
            jButtonSalvarEdicao.setEnabled(true);

        } else {
            // Mostra uma mensagem caso nenhum agendamento tenha sido selecionado
            JOptionPane.showMessageDialog(this, "Selecione um agendamento para editar.");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButtonSalvarEdicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarEdicaoActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTableAgendamentos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento para editar.");
            return;
        }

        // Obtenha os valores dos campos da interface
        String id = jTextFieldId.getText();
        String marca = jTextFieldMarca.getText();
        String placa = jTextFieldPlaca.getText();
        String proprietario = jTextFieldProprietario.getText();
        String contacto = jTextFieldContacto.getText();
        String tipoViatura = jComboBoxTipoViatura.getSelectedItem().toString();
        String horaEntrada = jTextFieldHoraEntrada.getText(); // Hora é string, sem conversão
        String horaSaida = jTextFieldHoraSaida.getText(); // Hora é string, sem conversão
        String totalText = jTextFieldTotal.getText();

        System.out.println("Marca: " + marca);
        System.out.println("Placa: " + placa);
        System.out.println("Proprietário: " + proprietario);
        System.out.println("Contacto: " + contacto);
        System.out.println("Tipo de Viatura: " + tipoViatura);
        System.out.println("Hora de Entrada: " + horaEntrada);
        System.out.println("Hora de Saída: " + horaSaida);

        // Verifique se todos os campos foram preenchidos corretamente
        if (marca.isEmpty() || placa.isEmpty() || proprietario.isEmpty() || contacto.isEmpty() || tipoViatura.isEmpty() || horaEntrada.isEmpty() || horaSaida.isEmpty() || totalText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }

        // Converter total para double e validar possíveis erros de formatação
        double total;
        try {
            total = Double.parseDouble(totalText); // Total pode ser double
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Formato inválido para o campo Total.");
            return;
        }

        // Atualize o registro no banco de dados
        try {
            java.sql.Connection con = Conexao.connect();
            String sql = "UPDATE agendamentos SET marca = ?, placa = ?, proprietario = ?, contacto = ?, tipo_viatura = ?, hora_entrada = ?, hora_saida = ?, total_servicos = ? WHERE id = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, marca);
            pst.setString(2, placa);
            pst.setString(3, proprietario);
            pst.setString(4, contacto);
            pst.setString(5, tipoViatura);
            pst.setString(6, horaEntrada); // Trate como string, sem conversão
            pst.setString(7, horaSaida); // Trate como string, sem conversão
            pst.setDouble(8, total); // Campo total é numérico
            pst.setInt(9, Integer.parseInt(id));  // O ID precisa ser passado para identificar o registro a ser editado

            // Executa o update
            int updatedRows = pst.executeUpdate();
            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(this, "Edição salva com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro: Nenhuma linha foi atualizada. Verifique o ID.");
            }

            pst.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar edição: " + e.getMessage());
        }
    }//GEN-LAST:event_jButtonSalvarEdicaoActionPerformed

    private void jButtonRemover1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemover1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTableAgendamentos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento para remover.");
            return;
        }

        // Obtenha o ID do agendamento selecionado
        int id = (int) jTableAgendamentos.getValueAt(selectedRow, 0); // O ID está na primeira coluna

        // Remover do banco de dados
        try {
            java.sql.Connection con = Conexao.connect();
            String sql = "DELETE FROM agendamentos WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Agendamento removido com sucesso!");

            pst.close();
            con.close();

            // Atualizar a tabela
            carregarAgendamentos(); // Atualiza a tabela após a remoção
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover agendamento: " + e.getMessage());
        }
    }//GEN-LAST:event_jButtonRemover1ActionPerformed

    private void AgendamentosFeitosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendamentosFeitosMouseClicked
       Paineis.setSelectedComponent(P12);
    }//GEN-LAST:event_AgendamentosFeitosMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home_Funcionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home_Funcionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home_Funcionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home_Funcionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home_Funcionario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AdicionarProduto;
    private javax.swing.JLabel Agendamentos;
    private javax.swing.JLabel AgendamentosFeitos;
    private javax.swing.JLabel AgendamentosPendentes;
    private javax.swing.JButton Cadastrar1;
    private javax.swing.JLabel Cadastrarcliente;
    private javax.swing.JPanel P10;
    private javax.swing.JPanel P12;
    private javax.swing.JPanel P13;
    private javax.swing.JPanel P14;
    private javax.swing.JPanel P15;
    private javax.swing.JPanel P16;
    private javax.swing.JPanel P17;
    private javax.swing.JPanel P2;
    private javax.swing.JPanel P3;
    private javax.swing.JPanel P4;
    private javax.swing.JPanel P5;
    private javax.swing.JPanel P7;
    private javax.swing.JTabbedPane Paineis;
    private javax.swing.JLabel Stock;
    private javax.swing.JLabel Ver_dados;
    private javax.swing.JButton adicionar;
    private javax.swing.JComboBox<String> categoriaComboBox;
    private javax.swing.JLabel clienteLabel;
    private javax.swing.JTable clienteTable;
    private javax.swing.JTextArea comentarioArea;
    private javax.swing.JLabel contacto1;
    private javax.swing.JTextField contactoField;
    private javax.swing.JLabel dataLabel;
    private javax.swing.JTextField dataValidadeField;
    private javax.swing.JTextField descontoField;
    private javax.swing.JButton editar;
    private javax.swing.JButton editar1;
    private javax.swing.JLabel email1;
    private javax.swing.JTextField emailField;
    private javax.swing.JButton excluir;
    private javax.swing.JButton excluir1;
    private javax.swing.JLabel feedback;
    private javax.swing.JTable feedbackTable;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonRemover1;
    private javax.swing.JButton jButtonSalvarAgendamento;
    private javax.swing.JButton jButtonSalvarEdicao;
    private javax.swing.JCheckBox jCheckBoxHibernizacao;
    private javax.swing.JCheckBox jCheckBoxLavagemCompleta;
    private javax.swing.JCheckBox jCheckBoxLavagemExterna;
    private javax.swing.JCheckBox jCheckBoxLavagemInterna;
    private javax.swing.JCheckBox jCheckBoxLavagemRodape;
    private javax.swing.JCheckBox jCheckBoxPolimento;
    private javax.swing.JComboBox<String> jComboBoxTipoMotor;
    private javax.swing.JComboBox<String> jComboBoxTipoViatura;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTableAgendamentos;
    private javax.swing.JTable jTableFeitos;
    private javax.swing.JTextField jTextFieldContacto;
    private javax.swing.JTextField jTextFieldHoraEntrada;
    private javax.swing.JTextField jTextFieldHoraSaida;
    private javax.swing.JTextField jTextFieldMarca;
    private javax.swing.JTextField jTextFieldMatricula;
    private javax.swing.JTextField jTextFieldPlaca;
    private javax.swing.JTextField jTextFieldProprietario;
    private javax.swing.JLabel jTextFieldTotal;
    private javax.swing.JLabel marca1;
    private javax.swing.JTextField marcaField;
    private javax.swing.JLabel matricula1;
    private javax.swing.JLabel modelo1;
    private javax.swing.JTextField modeloField;
    private javax.swing.JLabel morada1;
    private javax.swing.JTextField moradaField;
    private javax.swing.JLabel nome1;
    private javax.swing.JTextField nomeField;
    private javax.swing.JTextField nomeProdutoField;
    private javax.swing.JTextField pesquisajTextField;
    private javax.swing.JTextField promocaoField;
    private javax.swing.JSpinner quantidadeSpinner;
    private javax.swing.JLabel relatorio;
    private javax.swing.JLabel sexo1;
    private javax.swing.JComboBox<String> sexoComboBox;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JTable tabelaPromocoes;
    private javax.swing.JTextField validadeField;
    private javax.swing.JTextArea validadeTextArea;
    private javax.swing.JButton viewDetailsButton;
    // End of variables declaration//GEN-END:variables

    public static class setLocationRelativeTo {

        public setLocationRelativeTo(Object object) {
        }
    }
}
