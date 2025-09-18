create database if not exists locauto;

use locauto;

create table if not exists tb_pessoa(
    id_pessoa int primary key auto_increment,
    cpf varchar(11) not null,
    nome varchar(255) not null,
    data_nasc date,
    cep varchar(8),
    municipio varchar(255),
    uf varchar(2),
    complemento varchar(255),
    email varchar(255),
    telefone1 double,
    telefone2 double
);

create table if not exists tb_modelo_carro(
    id_modelo_carro int primary key auto_increment,
    marca varchar(255) not null,
    categoria varchar(255) not null,
    plataforma varchar(255) not null
);

create table if not exists tb_seguradora(
    id_seguradora int primary key auto_increment,
    cnpj varchar(14) not null,
    razao_social varchar(255)
);

create table if not exists tb_cliente(
    id_cliente int primary key auto_increment,
    fk_id_pessoa int,
    foreign key (fk_id_pessoa) references tb_pessoa(id_pessoa)
);

create table if not exists tb_carro(
    id_carro int primary key auto_increment,
    placa varchar(7),
    quilometragem double,
    cor varchar(255),
    status boolean,
    ano_fabricacao year,
    nome varchar(255),
    fk_id_modelo int,
    foreign key (fk_id_modelo) references tb_modelo_carro(id_modelo_carro)
);

create table if not exists tb_cnh_cliente(
    id_cnh int primary key auto_increment,
    categoria varchar(5) not null,
    fk_id_cliente int,
    foreign key (fk_id_cliente) references tb_cliente(id_cliente)
);

create table if not exists tb_contrato(
    id_contrato int primary key auto_increment,
    data_inicio date not null,
    data_fim date,
    valor_total decimal(10, 2),
    fk_id_cliente int,
    foreign key (fk_id_cliente) references tb_cliente(id_cliente)
);

create table if not exists tb_funcionario(
    id_funcionarios int primary key auto_increment,
    data_admissao date not null,
    cargo varchar(255) not null,
    salario double not null,
    fk_id_pessoa int,
    fk_id_contrato int,
    foreign key (fk_id_pessoa) references tb_pessoa(id_pessoa),
    foreign key (fk_id_contrato) references tb_contrato(id_contrato)
);

create table if not exists tb_cobertura(
    id_cobertura int primary key auto_increment,
    fk_id_contrato int,
    fk_id_carro int,
    fk_id_seguradora int,
    foreign key (fk_id_contrato) references tb_contrato(id_contrato),
    foreign key (fk_id_carro) references tb_carro(id_carro),
    foreign key (fk_id_seguradora) references tb_seguradora(id_seguradora)
);
