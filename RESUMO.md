# Resumo do que falta arrumar

Levantamento feito comparando o documento de requisitos (13 requisitos) com o código atual do backend Java e do frontend Angular.

Legenda: **[ ]** pendente · **[x]** feito

---

## Panorama dos 13 requisitos

| Req | Tema | Status |
|-----|------|--------|
| 1 | Médicos + vínculo com internação | Parcial |
| 2 | Agendamento de exames | Parcial |
| 3 | Tipo de leito + transferência | Ausente |
| 4 | Log de visitas | Ausente por completo |
| 5 | Diárias | Pronto |
| 6 | Remoção de médico | Ausente por completo |
| 7 | Medicamentos | Pronto |
| 8 | Convênio | Parcial (bug no update) |
| 9 | Entidade Exam | Parcial |
| 10 | Despesas da internação | Pronto |
| 11 | Fechamento financeiro | Parcial |
| 12 | Pagamento | Pronto |
| 13 | Relatório financeiro | Pronto |

---

## JAVA — Bugs em código que já existe

Estes quebram funcionalidades que hoje parecem prontas. São os de maior prioridade.

- [ ] **`HospitalService.update`** — chama `validateExistingHospital` com o CNPJ do próprio registro, então editar um hospital sem trocar o CNPJ sempre falha.
  Corrigir: validar só quando o CNPJ mudou.
- [ ] **`MedicalInsuranceRepository.existsByTypeAndIdNot`** — assinatura `(Long id, MedicalInsuranceType type)` está na ordem invertida em relação ao nome derivado, que espera `(type, id)`. Editar convênio quebra.
- [ ] **`ProvidedClosureService`** — `discount = subtotal.multiply(coverageRate)` sem dividir por 100. Se a taxa for cadastrada como `30` (30%), o desconto sai 30x e o total fica negativo.
- [ ] **`BedController`** — rota declara `{id}` mas o parâmetro se chama `bedId`.
- [ ] **`BuildWardService`** — `specialties.stream()` sem checar nulo; criar hospital sem o campo dá `NullPointerException`.

---

## JAVA — Requisitos ausentes por completo

- [ ] **Req 4 — Log de visitas.** Nenhuma linha existe. Criar entidade `Visit` (nome do visitante, documento, entrada, saída, FK para `Admission`) e a regra de "um visitante por vez".
- [ ] **Req 6 — Remoção de médico.** Sem `DELETE`, sem campo `active` para soft delete, sem validação de médico já vinculado a internação.
- [ ] **Req 3 — Transferência de leito.** Nenhum endpoint ou serviço de transferência.
- [ ] **Req 3 — Histórico de ocupação de leitos.** Não existe. O enum `EventType` (ADMISSION/DISCHARGE) já existe mas nunca é usado.
- [ ] **Req 3 — Leito UTI.** O construtor de `Bed` fixa `BedType.INFIRMARY` e o `BedRequest` só aceita quantidade. É impossível criar leito de UTI hoje, o que também inviabiliza cobrar as diárias de UTI do Req 5.

---

## JAVA — Regras pedidas que não foram implementadas

- [ ] **Req 9** — mudar exame para `CARRIED_OUT` não gera despesa em `ServicesProvided`. Também não existe endpoint para alterar o status.
- [ ] **Req 9** — `ExamScheduling` guarda o nome do exame como `String nameExam`, sem FK para `Exam`. Sem isso não há de onde tirar o valor unitário na hora de gerar a despesa, então isso é pré-requisito do item acima.
- [ ] **Req 11** — não bloqueia novas despesas depois do fechamento da conta.
- [ ] **Req 1** — não dá para vincular médicos no momento da internação; o `AdmissionRequest` só tem `bedId` e `patientId`.
- [ ] **Req 2** — a validação "médico solicitante é responsável pela internação" só roda no create, não no update.
- [ ] **Req 10** — médico não é exigido em despesas de `DRUG`/`EXAM`.

---

## JAVA — Enums fora da especificação

- [ ] **`ExamType`** — misturou categoria com nome de exame. Faltam `FUNCIONAL`, `ENDOSCOPICO`, `GENETICO_MOLECULAR`, `GINECOLOGICO_UROLOGICO`; sobram sete valores que são nomes de exame e deveriam estar no campo `nameExam`.
- [ ] **`MedicalInsuranceType`** — tem `SUL_AMERICANA`, o requisito pede `SUL_AMERICA`.
- [ ] **`ClosureStatus.CLOSED`** — existe mas nunca é atribuído; o fechamento vai de `OPEN` direto para `PAID`.
- [ ] **`EventType`** — existe mas nunca é usado.

---

## JAVA — Transversal

- [ ] **Nenhum `@RestControllerAdvice` no projeto.** Toda validação de negócio (CRM duplicado, estoque negativo, internação inativa) volta como HTTP 500 com corpo genérico. É o item de maior retorno sobre esforço: uma classe mapeando `AlreadyExistingEntityException` para 409, `EntityNotFoundException` para 404 e regras de negócio para 400 conserta a API inteira de uma vez.
- [ ] **Bean Validation ausente** em `doctor`, `drug`, `daily`, `hospital`, `patient`, `admission`, `bed`, `room` e `ward`. Os "dados obrigatórios" dos Req 1, 5 e 7 não são validados.
- [ ] **Faltam `findAll`** em `patient`, `bed`, `ward`, `room` e `admission`. Bloqueia tanto tabelas quanto os `<select>` do front.
- [ ] **`Daily` e `Exam` sem `@UniqueConstraint`** no banco; a unicidade só é validada no service, o que não segura requisições simultâneas.
- [ ] **`coverageRate` sem validação de faixa** (0–1 ou 0–100).
- [ ] **`Room.beds` sem `mappedBy`** — cria a tabela de junção redundante `room_beds`, já que `Bed` tem `room_id`.

---

## FRONT — Bugs

- [x] **Link morto para `/drug`** no `app.html` apontando para rota inexistente.
- [x] **`app.spec.ts` quebrado** — testava `<h1>Hello, frontend</h1>`, removido quando o placeholder do CLI foi apagado.
- [x] **`findALl`** no `doctor.service.ts` com o L maiúsculo trocado.

---

## FRONT — Telas

- [x] **Hospitais** — completa: listar, criar, editar, apagar e tratamento de erro.
  Observação: o botão Editar vai dar 500 enquanto o bug do `HospitalService.update` não for corrigido.
- [ ] **Médicos** — só a tabela. Falta o formulário de cadastro; o `create()` do service existe e nunca é chamado.
  Editar e apagar não são possíveis: o `DoctorController` não tem `PUT` nem `DELETE` (isso é o Req 6).

### Telas que ainda não existem

CRUD completo disponível no Java, dá para fazer igual ao hospital:

- [ ] **Drug** (`/api/drug`) — campos planos: `code`, `name`, `value`, `stock`. O mais fácil, melhor próximo passo.
- [ ] **Exam** (`/api/exam`) — `name`, `type` (enum), `value`.
- [ ] **MedicalInsurance** (`/api/medicalInsurance`) — `type` (enum), `coverageRate`. O editar depende do bug do repositório ser corrigido.
- [ ] **ExamScheduling** (`/api/exams`) — CRUD completo, mas exige digitar `admissionId` e `doctorId` na mão porque não há listagem de internações.

Só listar e criar:

- [ ] **ServicesProvided** (`/api/servicesprovided`)
- [ ] **Payment** (`/api/payment`) — mais um botão por linha para `PATCH /{id}/confirm`.
- [ ] **Closure** (`/api/closure`) — mais um botão por linha para `GET /{id}/report`, que é onde o Req 13 aparece na tela.

Sem tabela possível:

- [ ] **Daily** (`/api/daily`) — o `GET` exige `?type=&specialty=` e devolve um objeto só. Dá formulário de consulta, não tabela.

### Melhorias menores do front

- [ ] Tratamento de erro do `doctor-list` é mais pobre que o do hospital (mostra só `error.message`, sem o status HTTP).
- [ ] Nenhuma tela valida campo vazio antes de enviar.
- [ ] `getById` existe nos dois services e nunca é usado.
- [ ] Nenhum CSS; as tabelas usam `border` e `cellpadding` no HTML.

---

## Ordem sugerida

1. Os 5 bugs do Java — são pequenos e destravam coisa que já parece pronta.
2. O `@RestControllerAdvice` — melhora a API inteira de uma vez.
3. Os 5 `findAll` faltantes — destravam quase todas as telas restantes.
4. `ExamType` e a FK de `ExamScheduling` para `Exam` — pré-requisito da automação do Req 9.
5. Os 3 requisitos ausentes: Req 4 (visitas) é o mais simples, Req 3 (transferência + histórico) é o mais trabalhoso.
