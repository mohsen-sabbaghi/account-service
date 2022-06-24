$(document).ready(function () {
    $(function () {
        $('[data-bs-toggle="tooltip"]').tooltip()
        $('#tbl_customers').DataTable();
    })

    let global_customer_id;
    let global_name;
    let global_surname;

    var createCustomerModal = document.getElementById('createCustomerModal')
    createCustomerModal.addEventListener('show.bs.modal', function (event) {

    })

    var createAccountModal = document.getElementById('createAccountModal')
    createAccountModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget
        global_customer_id = button.getAttribute('data-bs-customerId');
        global_name = button.getAttribute('data-bs-customerName')
        global_surname = button.getAttribute('data-bs-customerSurName')
        var modalTitle = createAccountModal.querySelector('.modal-title')
        modalTitle.textContent = 'Add new Account to ' + global_name + ' ' + global_surname
    })

    $('#addAccountBtn').on('click', function () {
        createAccountForExistingCustomer(global_customer_id, $("#initialCredit").val())
    })

    $('#addCustomerBtn').on('click', function () {
        var name = $('#nameInput').val().trim();
        var surname = $('#surNameInput').val().trim();
        var json = {
            "name": name,
            "surname": surname
        };
        createNewCustomer(json)
    })
});

function createAccountForExistingCustomer(id, initialCredit) {
    $.ajax({
        type: "POST",
        beforeSend: function (request) {
            request.setRequestHeader("Initial-Credit", initialCredit);
            request.setRequestHeader("Track-Id", Math.floor((Math.random() * 10000) + 1));
        },
        url: "/v1/customers/" + id + "/accounts",
        processData: false,
        success: function (msg) {
            swal({
                title: "A new account with [" + initialCredit + "] credits",
                text: "has been created for the customer with the ID [" + id + "]",
                icon: "success",
                button: "ok"
            }).then(() => {
                location.reload();
            })
        },
        error: function (msg) {
            console.log("error", msg)
        }
    });
}

function createNewCustomer(jsonBody) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(jsonBody),
        cache: false,
        timeout: 600000,
        url: "/v1/customers/",
        processData: false,
        success: function (msg) {
            swal({
                title: msg.name + " " + msg.surname,
                text: "Saved as a new customer",
                icon: "success",
                button: "ok"
            }).then(() => {
                location.reload();
            })
        },
        error: function (msg) {
            swal({
                title: " Oops!",
                text: " Something went wrong, " + msg,
                icon: "error",
                button: "oh no!",
            });
        }
    });
}