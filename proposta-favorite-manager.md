# Favorite Manager - A Plataforma de Gestão de Produtos Favoritos

## **Descrição**
Imagine uma plataforma que conecta clientes aos seus produtos favoritos de forma simples, eficiente e personalizável. O **Favorite Manager** é um sistema inovador que permite que os usuários gerenciem sua lista de produtos favoritos, oferecendo uma experiência fluida e integrada para armazenamento, consulta e gerenciamento de preferências.

## **Problema Solucionado**
Com o aumento da oferta de produtos no mercado digital, muitos consumidores enfrentam o desafio de acompanhar e gerenciar suas preferências de compra. O **Favorite Manager** resolve esse problema, fornecendo uma solução para:
- Criar e manter listas de produtos favoritos.
- Acessar informações detalhadas sobre produtos em um único lugar.
- Personalizar a experiência do cliente, conectando seus interesses a uma base de dados externa.

## **Público-Alvo**
- Consumidores online que desejam organizar e gerenciar seus produtos favoritos.
- Empresas e lojas que buscam entender melhor as preferências de seus clientes.
- Provedores de dados que integram APIs externas para enriquecer a experiência do cliente.

---

## **Principais Funcionalidades**
1. **Gerenciamento de Clientes e Produtos Favoritos**:
    - Cadastro de clientes com validação de nome e e-mail.
    - Inclusão e atualização de dados de clientes.
    - Associação de múltiplos produtos favoritos a cada cliente.

2. **Integração com APIs Externas**:
    - Comunicação com APIs externas, como a API da LuizaLabs, para buscar informações atualizadas e detalhadas sobre os produtos.

3. **Cache para Melhoria de Performance**:
    - Utilização de cache com Spring para armazenar produtos consultados, otimizando o tempo de resposta.

4. **Operações Completas nos Favoritos**:
    - Adição, remoção e consulta de favoritos.
    - Busca de um único favorito ou da lista completa por cliente.

5. **Sistema Resiliente com Feedback ao Usuário**:
    - Tratamento de erros amigáveis, como produtos duplicados, cliente inexistente ou lista de favoritos vazia.

6. **APIs Documentadas e Funcionais**:
    - Endpoints RESTful claros e bem organizados para facilitar a utilização por clientes e integrações externas.

---

## **Diferenciais Técnicos**
1. **Arquitetura Limpa e Escalável**:
   Utilizando conceitos de **Clean Architecture** e **Hexagonal Architecture**, o sistema garante:
    - Baixo acoplamento.
    - Alta testabilidade.
    - Facilidade de manutenção e evolução.

2. **Testes Automatizados**:
    - O sistema conta com uma robusta suíte de testes que garante sua estabilidade e qualidade, cobrindo cenários como criação de clientes, busca de favoritos e comunicações integradas.

3. **Integrado e Performático**:
    - Implementação de FeignClient para fácil conexão com APIs externas.
    - Configuração de cache e logs detalhados para rastreabilidade.

4. **Abordagem de Contratos e Portas de Entrada**:
    - Separação de responsabilidades através de portas e adaptadores, permitindo integrar facilmente novos canais (e.g., outra API de marketplace ou sistemas adicionais de clientes).

---

## **Exemplo de Uso**
1. Um novo cliente se cadastra no sistema inserindo suas informações pessoais.
2. Ao navegar por um marketplace integrado, encontra produtos interessantes e os adiciona à sua lista de favoritos.
3. Em um momento futuro, o cliente pode facilmente consultar seus produtos favoritos com todos os detalhes (como preço, imagem e avaliações), removê-los ou adicionar novos.

---

## **Impacto e Objetivo Final**
Com o **Favorite Manager**, os consumidores ganham mais controle sobre suas preferências de produtos, enquanto as empresas conseguem oferecer experiências mais personalizadas. Isso permite um aumento no engajamento, na satisfação dos usuários e na conversão de vendas.

Esse sistema é **flexível**, **escalável** e altamente **integrável**, tornando-se uma solução central no contexto de plataformas de consumo digital.

---

## **Resumo Técnico do Sistema**
- **Backend**: Spring Boot com OpenFeign e cache embutido.
- **Banco de Dados**: Estruturado via Spring Data JPA.
- **Técnicas aplicadas**: Testes de Unidade e Integração, Clean Code, e Modelagem Orientada a Domínio (DDD).
- **Futuro**: Pronto para serviços em nuvem e integração com diversos provedores de produtos.
