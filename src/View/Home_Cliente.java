package View;


import Control.Login;
import connect.Conexao;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Home_Cliente extends javax.swing.JFrame {

    

    public Home_Cliente() {
        initComponents();
        carregarPromocoesNaTabela();
        setLocationRelativeTo(null);
        
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
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar Promoções: " + e.getMessage());
    }
}

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        P3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Agendamentos = new javax.swing.JLabel();
        Cadastrar = new javax.swing.JLabel();
        Servicosfeitos = new javax.swing.JLabel();
        Feedback = new javax.swing.JLabel();
        Promocoes = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        Home = new javax.swing.JLabel();
        Paineis = new javax.swing.JTabbedPane();
        P8 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        Back = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        P5 = new javax.swing.JPanel();
        P9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        clienteField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        comentarioArea = new javax.swing.JTextArea();
        EnviarFeedback = new javax.swing.JButton();
        P10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaPromocoes = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        detalhamentoTextArea = new javax.swing.JTextArea();
        ActualizarPromoção = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        P12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        sexoLabel1 = new javax.swing.JLabel();
        nomeField = new javax.swing.JTextField();
        emailLabel1 = new javax.swing.JLabel();
        contactoField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        cadastro1 = new javax.swing.JButton();
        moradaLabel1 = new javax.swing.JLabel();
        moradaField = new javax.swing.JTextField();
        nomeLabel1 = new javax.swing.JLabel();
        sexoComboBox = new javax.swing.JComboBox<>();
        marcaField = new javax.swing.JTextField();
        marca1 = new javax.swing.JLabel();
        modeloField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldMatricula = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox<>();
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
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTextFieldProprietario = new javax.swing.JTextField();
        jComboBoxTipoViatura = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jTextFieldHoraEntrada = new javax.swing.JTextField();
        jTextFieldHoraSaida = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextFieldContacto2 = new javax.swing.JTextField();
        jComboBoxTipoMotor = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();

        P3.setBackground(new java.awt.Color(0, 204, 204));
        P3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("BEM-VINDO CLIENTE");
        P3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, 270, 70));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Imagens/pngwing.com 2.png"))); // NOI18N
        P3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, -40, 1220, 690));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1300, 680));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(1080, 680));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Agendamentos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Agendamentos.setForeground(new java.awt.Color(255, 255, 255));
        Agendamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-agenda-32.png"))); // NOI18N
        Agendamentos.setText("Agendamentos");
        Agendamentos.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Agendamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AgendamentosMouseClicked(evt);
            }
        });
        jPanel1.add(Agendamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 150, -1));

        Cadastrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Cadastrar.setForeground(new java.awt.Color(255, 255, 255));
        Cadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-add-administrator-32.png"))); // NOI18N
        Cadastrar.setText("Cadastrar");
        Cadastrar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Cadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CadastrarMouseClicked(evt);
            }
        });
        jPanel1.add(Cadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 150, -1));

        Servicosfeitos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Servicosfeitos.setForeground(new java.awt.Color(255, 255, 255));
        Servicosfeitos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-services-32.png"))); // NOI18N
        Servicosfeitos.setText("Servicos Feitos");
        Servicosfeitos.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Servicosfeitos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ServicosfeitosMouseClicked(evt);
            }
        });
        jPanel1.add(Servicosfeitos, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 440, 150, -1));

        Feedback.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Feedback.setForeground(new java.awt.Color(255, 255, 255));
        Feedback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8_feedback_32px_3.png"))); // NOI18N
        Feedback.setText("FeedBacks");
        Feedback.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Feedback.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FeedbackMouseClicked(evt);
            }
        });
        jPanel1.add(Feedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 560, 150, -1));

        Promocoes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Promocoes.setForeground(new java.awt.Color(255, 255, 255));
        Promocoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8-marketing-32.png"))); // NOI18N
        Promocoes.setText("Promocoes");
        Promocoes.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Promocoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PromocoesMouseClicked(evt);
            }
        });
        jPanel1.add(Promocoes, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, 150, -1));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Imagens/ABSA_Car_wash-removebg-preview (1).png"))); // NOI18N
        jPanel1.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/home-40.png"))); // NOI18N
        Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HomeMouseClicked(evt);
            }
        });
        jPanel1.add(Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 680));

        P8.setBackground(new java.awt.Color(0, 204, 204));
        P8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("BEM-VINDO CLIENTE");
        P8.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 330, 70));

        Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Icones/icons8_left_arrow_30px_1.png"))); // NOI18N
        Back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackMouseClicked(evt);
            }
        });
        P8.add(Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 30, 20));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Imagens/pngwing.com 2.png"))); // NOI18N
        P8.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        Paineis.addTab("tab1", P8);

        P5.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout P5Layout = new javax.swing.GroupLayout(P5);
        P5.setLayout(P5Layout);
        P5Layout.setHorizontalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1630, Short.MAX_VALUE)
        );
        P5Layout.setVerticalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 675, Short.MAX_VALUE)
        );

        Paineis.addTab("tab2", P5);

        P9.setBackground(new java.awt.Color(204, 255, 255));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("FeeBack");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Cliente");

        clienteField.setBackground(new java.awt.Color(255, 255, 255));
        clienteField.setForeground(new java.awt.Color(0, 0, 0));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Comentario");
        jLabel11.setToolTipText("");

        comentarioArea.setBackground(new java.awt.Color(255, 255, 255));
        comentarioArea.setColumns(20);
        comentarioArea.setRows(5);
        jScrollPane1.setViewportView(comentarioArea);

        EnviarFeedback.setBackground(new java.awt.Color(0, 153, 255));
        EnviarFeedback.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        EnviarFeedback.setForeground(new java.awt.Color(0, 0, 0));
        EnviarFeedback.setText("Enviar Feedback");
        EnviarFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarFeedbackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout P9Layout = new javax.swing.GroupLayout(P9);
        P9.setLayout(P9Layout);
        P9Layout.setHorizontalGroup(
            P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P9Layout.createSequentialGroup()
                .addGroup(P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(P9Layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addGroup(P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(P9Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(P9Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(42, 42, 42)
                                .addComponent(clienteField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(P9Layout.createSequentialGroup()
                        .addGap(576, 576, 576)
                        .addComponent(EnviarFeedback))
                    .addGroup(P9Layout.createSequentialGroup()
                        .addGap(562, 562, 562)
                        .addComponent(jLabel3)))
                .addContainerGap(775, Short.MAX_VALUE))
        );
        P9Layout.setVerticalGroup(
            P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P9Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel3)
                .addGap(92, 92, 92)
                .addGroup(P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(clienteField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(EnviarFeedback)
                .addGap(66, 66, 66))
        );

        Paineis.addTab("tab9", P9);

        P10.setBackground(new java.awt.Color(18, 181, 185));

        tabelaPromocoes.setBackground(new java.awt.Color(204, 204, 204));
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
        tabelaPromocoes.setSelectionBackground(new java.awt.Color(153, 204, 255));
        tabelaPromocoes.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tabelaPromocoes);

        detalhamentoTextArea.setBackground(new java.awt.Color(204, 204, 204));
        detalhamentoTextArea.setColumns(20);
        detalhamentoTextArea.setForeground(new java.awt.Color(0, 0, 0));
        detalhamentoTextArea.setRows(5);
        jScrollPane3.setViewportView(detalhamentoTextArea);

        ActualizarPromoção.setBackground(new java.awt.Color(51, 102, 255));
        ActualizarPromoção.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ActualizarPromoção.setForeground(new java.awt.Color(0, 0, 0));
        ActualizarPromoção.setText("Actualizar Promoção");
        ActualizarPromoção.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarPromoçãoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe Script", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Promoção");

        javax.swing.GroupLayout P10Layout = new javax.swing.GroupLayout(P10);
        P10.setLayout(P10Layout);
        P10Layout.setHorizontalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, P10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ActualizarPromoção)
                .addGap(515, 515, 515))
            .addGroup(P10Layout.createSequentialGroup()
                .addGroup(P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(P10Layout.createSequentialGroup()
                        .addGap(515, 515, 515)
                        .addComponent(jLabel1)))
                .addContainerGap(518, Short.MAX_VALUE))
        );
        P10Layout.setVerticalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P10Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel1)
                .addGap(44, 44, 44)
                .addGroup(P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3))
                .addGap(18, 18, 18)
                .addComponent(ActualizarPromoção)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        Paineis.addTab("tab10", P10);

        P12.setBackground(new java.awt.Color(58, 175, 169));
        P12.setForeground(new java.awt.Color(0, 0, 0));
        P12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P12.setDebugGraphicsOptions(javax.swing.DebugGraphics.BUFFERED_OPTION);
        P12.setMinimumSize(new java.awt.Dimension(1180, 545));
        P12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("CADASTRO ");
        P12.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, -1, 38));

        sexoLabel1.setBackground(new java.awt.Color(0, 0, 0));
        sexoLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sexoLabel1.setForeground(new java.awt.Color(0, 0, 0));
        sexoLabel1.setText("Sexo");
        sexoLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        P12.add(sexoLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, -1, -1));

        nomeField.setBackground(new java.awt.Color(255, 255, 255));
        nomeField.setForeground(new java.awt.Color(0, 0, 0));
        nomeField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        P12.add(nomeField, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 170, 170, 30));

        emailLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        emailLabel1.setForeground(new java.awt.Color(0, 0, 0));
        emailLabel1.setText("Email");
        P12.add(emailLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 270, -1, -1));

        contactoField.setBackground(new java.awt.Color(255, 255, 255));
        contactoField.setForeground(new java.awt.Color(0, 0, 0));
        contactoField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contactoFieldKeyTyped(evt);
            }
        });
        P12.add(contactoField, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 310, 170, 30));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Contacto");
        P12.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 320, -1, -1));

        emailField.setBackground(new java.awt.Color(255, 255, 255));
        emailField.setForeground(new java.awt.Color(0, 0, 0));
        emailField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                emailFieldKeyTyped(evt);
            }
        });
        P12.add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 260, 170, 30));

        cadastro1.setBackground(new java.awt.Color(46, 213, 115));
        cadastro1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        cadastro1.setForeground(new java.awt.Color(0, 0, 0));
        cadastro1.setText("Cadastrar");
        cadastro1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cadastro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastro1ActionPerformed(evt);
            }
        });
        P12.add(cadastro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 540, -1, -1));

        moradaLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        moradaLabel1.setForeground(new java.awt.Color(0, 0, 0));
        moradaLabel1.setText("Morada");
        P12.add(moradaLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 370, -1, -1));

        moradaField.setBackground(new java.awt.Color(255, 255, 255));
        moradaField.setForeground(new java.awt.Color(0, 0, 0));
        P12.add(moradaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 370, 170, 30));

        nomeLabel1.setBackground(new java.awt.Color(0, 0, 0));
        nomeLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nomeLabel1.setForeground(new java.awt.Color(0, 0, 0));
        nomeLabel1.setText("Nome");
        P12.add(nomeLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 60, -1));

        sexoComboBox.setBackground(new java.awt.Color(255, 255, 255));
        sexoComboBox.setForeground(new java.awt.Color(0, 0, 0));
        sexoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));
        P12.add(sexoComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 170, 30));

        marcaField.setBackground(new java.awt.Color(255, 255, 255));
        P12.add(marcaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 170, 176, 30));

        marca1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        marca1.setForeground(new java.awt.Color(0, 0, 0));
        marca1.setText("Marca do Carro");
        P12.add(marca1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 170, -1, -1));

        modeloField.setBackground(new java.awt.Color(255, 255, 255));
        P12.add(modeloField, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 222, 176, 30));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Modelo do Carro");
        P12.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 230, -1, -1));

        jTextFieldMatricula.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldMatricula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldMatriculaKeyTyped(evt);
            }
        });
        P12.add(jTextFieldMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 270, 176, 30));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Matricula");
        P12.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 280, -1, -1));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Status");
        P12.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 330, -1, -1));

        statusComboBox.setBackground(new java.awt.Color(255, 255, 255));
        statusComboBox.setForeground(new java.awt.Color(0, 0, 0));
        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "inativo" }));
        P12.add(statusComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 320, 180, 30));

        Paineis.addTab("tab6", P12);

        P7.setBackground(new java.awt.Color(0, 204, 153));
        P7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Marca");
        P7.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Placa");
        P7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, -1, 28));

        jCheckBoxLavagemInterna.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemInterna.setText("Lavagem Interna");
        P7.add(jCheckBoxLavagemInterna, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, -1, -1));

        jCheckBoxLavagemExterna.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemExterna.setText("Lavagem Externa");
        P7.add(jCheckBoxLavagemExterna, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 410, -1, -1));

        jCheckBoxPolimento.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxPolimento.setText("Polimento e Enceramento");
        P7.add(jCheckBoxPolimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, -1, -1));

        jCheckBoxLavagemCompleta.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemCompleta.setText("Lavagem Completa");
        P7.add(jCheckBoxLavagemCompleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 390, -1, -1));

        jCheckBoxHibernizacao.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxHibernizacao.setText("Hibernização e Tratamento de Bancos");
        P7.add(jCheckBoxHibernizacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 360, -1, -1));

        jTextFieldMarca.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(jTextFieldMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, 138, -1));

        jTextFieldPlaca.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(jTextFieldPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 138, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("AGENDAMENTOS");
        P7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Valor A pagar");
        P7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, -1, -1));

        jButtonSalvarAgendamento.setBackground(new java.awt.Color(255, 255, 255));
        jButtonSalvarAgendamento.setForeground(new java.awt.Color(0, 0, 0));
        jButtonSalvarAgendamento.setText("Salvar");
        jButtonSalvarAgendamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarAgendamentoActionPerformed(evt);
            }
        });
        P7.add(jButtonSalvarAgendamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 510, -1, -1));

        jTextFieldTotal.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldTotal.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldTotal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        P7.add(jTextFieldTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 610, 100, 25));

        jCheckBoxLavagemRodape.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxLavagemRodape.setText("Lavagem Externa");
        P7.add(jCheckBoxLavagemRodape, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 400, -1, -1));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Contacto");
        P7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, -1, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Proprietário");
        P7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 140, -1, -1));

        jTextFieldProprietario.setBackground(new java.awt.Color(255, 255, 255));
        P7.add(jTextFieldProprietario, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 140, 120, -1));

        jComboBoxTipoViatura.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxTipoViatura.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxTipoViatura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pesado", "Especial", "Ligeiro Pessoal", "Motocicleta" }));
        P7.add(jComboBoxTipoViatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 280, 130, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Hora de Entrada");
        P7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 90, -1, 20));

        jTextFieldHoraEntrada.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldHoraEntrada.setForeground(new java.awt.Color(0, 0, 0));
        P7.add(jTextFieldHoraEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 90, 120, -1));

        jTextFieldHoraSaida.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldHoraSaida.setForeground(new java.awt.Color(0, 0, 0));
        P7.add(jTextFieldHoraSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 140, 120, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Hora de Saida");
        P7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 140, -1, -1));

        jTextFieldContacto2.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldContacto2.setForeground(new java.awt.Color(0, 0, 0));
        P7.add(jTextFieldContacto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, 120, -1));

        jComboBoxTipoMotor.setBackground(new java.awt.Color(255, 255, 255));
        jComboBoxTipoMotor.setForeground(new java.awt.Color(0, 0, 0));
        jComboBoxTipoMotor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "VVTI", "V2 e V3", "V6", "V8" }));
        P7.add(jComboBoxTipoMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 280, 110, -1));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Categoria");
        P7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, 60, 20));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Tipo de Motor");
        P7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 280, -1, -1));

        Paineis.addTab("tab4", P7);

        getContentPane().add(Paineis, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, -30, -1, 710));

        setSize(new java.awt.Dimension(1316, 692));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
        
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
            evt.consume(); // Cancelar o evento se os dois primeiros dígitos não forem válidos
            JOptionPane.showMessageDialog(null, "Os dois primeiros dígitos devem ser 82, 83, 84, 85, 86 ou 87.",
                "Validação de Contato", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Se a validação passar, você pode definir a cor do texto para indicar que está tudo certo
        contactoField.setForeground(java.awt.Color.BLACK); // Mudar a cor do texto para verde

    }//GEN-LAST:event_contactoFieldKeyTyped

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

    private void cadastro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastro1ActionPerformed
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
if (!status.equals("ativo")) {
    JOptionPane.showMessageDialog(null, "Status inválido. Os valores permitidos são 'ativo'.", 
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

    }//GEN-LAST:event_cadastro1ActionPerformed

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
            jTextFieldMatricula.setForeground(java.awt.Color.RED); // Mudar a cor do texto para vermelho se não corresponder
            // Opcional: você pode exibir uma mensagem aqui também
        } else {
            jTextFieldMatricula.setForeground(java.awt.Color.BLACK); // Mudar a cor do texto para verde se corresponder
        }

    }//GEN-LAST:event_jTextFieldMatriculaKeyTyped

    private void AgendamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendamentosMouseClicked
      // Verificar o status do cliente
    String statusCliente = getStatusCliente(); // Supondo que você tenha um método para obter o status

    if ("ativo".equalsIgnoreCase(statusCliente)) {
        // Se o status do cliente for ativo, abrir o painel de cadastro
        Paineis.setSelectedComponent(P7);
    } else {
        // Se o status do cliente não for ativo, mostrar uma mensagem
        JOptionPane.showMessageDialog(this, "Por favor, aguarde a confirmação do seu cadastro.", "Cadastro Pendente", JOptionPane.WARNING_MESSAGE);
    }
}


    private String getStatusCliente() {
    
    return "pendente"; // Altere conforme necessário
    }//GEN-LAST:event_AgendamentosMouseClicked

    private void CadastrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CadastrarMouseClicked
        Paineis.setSelectedComponent(P12);
    }//GEN-LAST:event_CadastrarMouseClicked

    private void ServicosfeitosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ServicosfeitosMouseClicked
         Paineis.setSelectedComponent(P5);
    }//GEN-LAST:event_ServicosfeitosMouseClicked

    private void PromocoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PromocoesMouseClicked
        Paineis.setSelectedComponent(P10);
    }//GEN-LAST:event_PromocoesMouseClicked

    private void FeedbackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FeedbackMouseClicked
        Paineis.setSelectedComponent(P9);
    }//GEN-LAST:event_FeedbackMouseClicked

    private void jButtonSalvarAgendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarAgendamentoActionPerformed
         // Obtenha os valores dos campos
        String marca = jTextFieldMarca.getText();
        String placa = jTextFieldPlaca.getText();
        String proprietario = jTextFieldProprietario.getText();
        String contacto = jTextFieldContacto2.getText();
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

    private void EnviarFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarFeedbackActionPerformed
    String cliente = clienteField.getText().trim(); // Obter o nome do cliente
    String comentario = comentarioArea.getText().trim(); // Obter o comentário

    // Verifica se os campos estão preenchidos
    if (cliente.isEmpty() || comentario.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Obtém a data atual
    java.sql.Date dataAtual = new java.sql.Date(System.currentTimeMillis());

    // Conectar ao banco de dados e adicionar feedback
    String url = "jdbc:mysql://localhost:3306/testesdb"; // Ajuste o URL conforme necessário
    String user = "root";
    String password = ""; // Substitua pela senha correta

    try (Connection connection = DriverManager.getConnection(url, user, password);
         PreparedStatement statement = connection.prepareStatement("INSERT INTO feedbacks (cliente, data, comentario) VALUES (?, ?, ?)")) {

        // Define os parâmetros da query
        statement.setString(1, cliente);
        statement.setDate(2, dataAtual);
        statement.setString(3, comentario);

        // Executa a query
        int rowsAffected = statement.executeUpdate();
        
        if (rowsAffected > 0) {
         
            // Limpa os campos após enviar
            clienteField.setText("");
            comentarioArea.setText("");

            // Mensagem de sucesso
            JOptionPane.showMessageDialog(this, "Feedback enviado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum feedback foi adicionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao enviar feedback: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_EnviarFeedbackActionPerformed

    private void ActualizarPromoçãoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarPromoçãoActionPerformed
                                                    
    int selectedRow = tabelaPromocoes.getSelectedRow(); // Obtém a linha selecionada

    if (selectedRow != -1) { // Verifica se alguma linha está selecionada
        // Obtém os dados da promoção selecionada
        String nomePromocao = (String) tabelaPromocoes.getValueAt(selectedRow, 0); // Nome da promoção
        double desconto = (Double) tabelaPromocoes.getValueAt(selectedRow, 1); // Desconto
        Date validade = (Date) tabelaPromocoes.getValueAt(selectedRow, 2); // Validade

        // Formata a informação a ser exibida
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("Nome da Promoção: ").append(nomePromocao).append("\n");
        detalhes.append("Desconto: ").append(desconto).append("%\n");
        detalhes.append("Validade: ").append(validade.toString()).append("\n");

        // Adiciona as informações ao JTextArea
        detalhamentoTextArea.setText(detalhes.toString()); // Exibe os detalhes no JTextArea
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, selecione uma promoção na tabela."); // Mensagem de erro
    }

    }//GEN-LAST:event_ActualizarPromoçãoActionPerformed

    private void BackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackMouseClicked
        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_BackMouseClicked

    private void HomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseClicked
         Paineis.setSelectedComponent(P8);
    }//GEN-LAST:event_HomeMouseClicked

    /**}
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
            java.util.logging.Logger.getLogger(Home_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home_Cliente().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ActualizarPromoção;
    private javax.swing.JLabel Agendamentos;
    private javax.swing.JLabel Back;
    private javax.swing.JLabel Cadastrar;
    private javax.swing.JButton EnviarFeedback;
    private javax.swing.JLabel Feedback;
    private javax.swing.JLabel Home;
    private javax.swing.JPanel P10;
    private javax.swing.JPanel P12;
    private javax.swing.JPanel P3;
    private javax.swing.JPanel P5;
    private javax.swing.JPanel P7;
    private javax.swing.JPanel P8;
    private javax.swing.JPanel P9;
    private javax.swing.JTabbedPane Paineis;
    private javax.swing.JLabel Promocoes;
    private javax.swing.JLabel Servicosfeitos;
    private javax.swing.JButton cadastro1;
    private javax.swing.JTextField clienteField;
    private javax.swing.JTextArea comentarioArea;
    private javax.swing.JTextField contactoField;
    private javax.swing.JTextArea detalhamentoTextArea;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel1;
    private javax.swing.JButton jButtonSalvarAgendamento;
    private javax.swing.JCheckBox jCheckBoxHibernizacao;
    private javax.swing.JCheckBox jCheckBoxLavagemCompleta;
    private javax.swing.JCheckBox jCheckBoxLavagemExterna;
    private javax.swing.JCheckBox jCheckBoxLavagemInterna;
    private javax.swing.JCheckBox jCheckBoxLavagemRodape;
    private javax.swing.JCheckBox jCheckBoxPolimento;
    private javax.swing.JComboBox<String> jComboBoxTipoMotor;
    private javax.swing.JComboBox<String> jComboBoxTipoViatura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextFieldContacto2;
    private javax.swing.JTextField jTextFieldHoraEntrada;
    private javax.swing.JTextField jTextFieldHoraSaida;
    private javax.swing.JTextField jTextFieldMarca;
    private javax.swing.JTextField jTextFieldMatricula;
    private javax.swing.JTextField jTextFieldPlaca;
    private javax.swing.JTextField jTextFieldProprietario;
    private javax.swing.JLabel jTextFieldTotal;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel marca1;
    private javax.swing.JTextField marcaField;
    private javax.swing.JTextField modeloField;
    private javax.swing.JTextField moradaField;
    private javax.swing.JLabel moradaLabel1;
    private javax.swing.JTextField nomeField;
    private javax.swing.JLabel nomeLabel1;
    private javax.swing.JComboBox<String> sexoComboBox;
    private javax.swing.JLabel sexoLabel1;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JTable tabelaPromocoes;
    // End of variables declaration//GEN-END:variables
}