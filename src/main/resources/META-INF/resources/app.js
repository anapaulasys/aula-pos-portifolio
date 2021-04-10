const url = "http://localhost:8080/api";
var perfil = {};
var habilidades = [];
var habilidadeSelecionada = {};

function httpRequest(url, type, success, data, contentType = 'application/json') {
    $.ajax({
        url: url,
        type: type,
        data: data,
        contentType: contentType,
        success: success,
        error: function (error) {
            console.log(error);
        }
    });
}

function onSuccessGetPerfil(response) {
    if (response) {
        perfil = response;
        $('#perfil-nome').html(response.nome);
        $('#perfil-fone').html(response.telefone);
        $('#perfil-email').html(response.email);
        $('#perfil-bio').html(response.bio);

        $('#perfil-foto').attr("src", response.foto);

    } else {
        perfil = {};
    }
}

function getPerfil() {
    httpRequest(url + '/perfil', 'GET', onSuccessGetPerfil);
}

function editPerfil() {
    $('#edit-perfil-nome').val(perfil.nome);
    $('#edit-perfil-email').val(perfil.email);
    $('#edit-perfil-fone').val(perfil.telefone);
    $('#edit-perfil-bio').val(perfil.bio);
}

function savePerfil() {
    perfil.nome = $('#edit-perfil-nome').val();
    perfil.email = $('#edit-perfil-email').val();
    perfil.telefone = $('#edit-perfil-fone').val();
    perfil.bio = $('#edit-perfil-bio').val();
    httpRequest(url + '/perfil', 'POST', getPerfil, JSON.stringify(perfil));
}

function newHabilidade() {
    $('#edit-habilidade-nome').val("");
    $('#edit-habilidade-descricao').val("");
}

function saveHabilidade() {
    habilidadeSelecionada.nome = $('#edit-habilidade-nome').val();
    habilidadeSelecionada.descricao = $('#edit-habilidade-descricao').val();
    httpRequest(url + '/habilidade', habilidadeSelecionada.id ? 'PUT' : 'POST', getHabilidades, JSON.stringify(habilidadeSelecionada));
}

function editHabilidades(index) {
    habilidadeSelecionada = habilidades[index];
    $('#edit-habilidade-nome').val(habilidadeSelecionada.nome);
    $('#edit-habilidade-descricao').val(habilidadeSelecionada.descricao);
    $('#edit-habilidade-modal').modal('show');
}

function onSuccessHabilidades(response) {
    habilidades = response;
    $("#lista-habilidades").html("");
    for (let i = 0; i < response.length; i++) {
        const habilidade = response[i];
        const li = $("<li style='cursor: pointer' onclick=\"editHabilidades(" + i + ")\" class=\"list-group-item d-flex justify-content-between align-items-start\"> </li>");
        const div = $("<div class='ms-2 me-auto'>");
        const divNome = $("<div class='fw-bold'>");
        divNome.append(habilidade.nome);
        div.append(divNome);
        div.append(habilidade.descricao);
        li.append(div);
        $("#lista-habilidades").append(li);
    }
    habilidadeSelecionada = {};
}

function onSuccessProjetos(response) {
    $("#lista-projetos").html("");
    for (let i = 0; i < response.length; i++) {
        const projeto = response[i];
        const li = $("<li style='cursor: pointer' class=\"list-group-item d-flex justify-content-between align-items-start\"> </li>");
        const div = $("<div class='ms-2 me-auto'>");
        const divNome = $("<div class='fw-bold'>");
        divNome.append(projeto.name);
        div.append(divNome);
        div.append(projeto.description);
        li.append(div);
        $("#lista-projetos").append(li);
    }
    habilidadeSelecionada = {};
}

function getHabilidades() {
    httpRequest(url + '/habilidade', 'GET', onSuccessHabilidades);
}

function getProjeto() {
    httpRequest(url + '/projeto', 'GET', onSuccessProjetos);
}


function uploadFoto() {
    let arquivo = $("#edit-perfil-foto");

    if (arquivo && arquivo[0] && arquivo[0].files.length > 0) {
        var foto = arquivo[0].files[0];

        var form = new FormData();
        form.append('perfil-foto', foto);

        $.ajax({
            url: url + '/upload',
            type: "POST",
            data: form,
            contentType: false,
            processData: false,
            success: getPerfil,
            error: function (error) {
                console.log(error);
            }
        });

    } else {
        alert("Selecione um arquivo");
    }
};



$(document).ready(function () {
    getPerfil();
    getHabilidades();
    getProjeto();
});