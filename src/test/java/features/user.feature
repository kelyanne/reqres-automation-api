Feature: Testes na API de Users

  Scenario: Realizar requisição GET para listar todos usuários da página 1
    Given eu faço uma requisição get ao endpoint "/users"
    Then o endpoint retorna o status 200
    And lista todos os 6 usuários da página

  Scenario: Realizar requisição POST para persistir um usuário no banco de dados
    Given eu informo os dados do usuário a ser persistido
    When e faço uma requisição post ao endpoint "/users"
    Then o endpoint retorna o status 201 e persisite o usuário com sucesso

  Scenario: Realizar requisição PUT para atualizar o usuário cadastrado
    Given eu informo os dados do usuário a serem atualizados
    When e faço uma requisição put ao endpoint "/users/" + userId
    Then o endpoint retorna o status 200 e atualiza todos os dados do usuário com sucesso

  Scenario: Realizar requisição PATCH para atualizar o usuário cadastrado
    Given eu informo o novo cargo do usuário a ser atualizado
    When e faço uma requisição patch ao endpoint "/users/" + userId
    Then o endpoint retorna o status 200 e atualiza o cargo do usuário com sucesso

  Scenario: Realizar requisição DELETE para deletar o usuário cadastrado
    Given eu faço uma requisição delete ao endpoint "/users/" + userId
    Then o endpoint retorna o status 204 e deleta o usuário com sucesso