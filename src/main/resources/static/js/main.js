
$(document).ready(function () {
    //getActiveUser();
    getAllUsers();
});




function getAllUsers() {
    fetch('http://localhost:8080/api/users')
        .then((response) => response.json())
        .then((userList) => {
            let table = "";
            userList.forEach(user => {

                let rolesAsString = "";
                user.roles.forEach(role => rolesAsString += role.role + " ");

                let tableRow = '<tr>' +
                    "<td>" + user.id + "</td>" +
                    "<td>" + user.name + "</td>" +
                    "<td>" + user.surname + "</td>" +
                    "<td>" + user.age + "</td>" +
                    "<td>" + user.email + "</td>" +
                    "<td>" + rolesAsString + "</td>" +
                    '<td><button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editUserModal"' +
                    ' onclick="editUser(' + user.id + ')" id="btnEdit">Edit</button></td>' +
                    '<td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteUserModal"' +
                    ' onclick="getUserForDeleteModal(' + user.id + ')" id="btnDelete">Delete</button></td>'
                table += tableRow;
            });
            document.getElementById("allUsers").innerHTML += table;
        });
}




function addNewUser() {
    let addUserForm = document.forms['newUserForm']

    let listRoles = Array.from(document.getElementById('selectNewRole').selectedOptions).map(role => role.value);

    fetch('http://localhost:8080/api/users', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            name: addUserForm.name.value,
            surname: addUserForm.surname.value,
            age: addUserForm.age.value,
            email: addUserForm.email.value,
            password: addUserForm.password.value,
            roles: listRoles

        })
    });
}






function editUser(id) {
    let closeButton = document.getElementById("closeButtonInEditModal");
    let editButton = document.getElementById("editButtonInEditModal");
    let modalEdit = document.getElementById("editUserModal");
    let closeButtonHeader = document.getElementById("closeButtonInEditModalHeader");
    let editUserForm = document.forms['editUserForm']


    fetch('http://localhost:8080/api/users/' + id)
        .then(response => response.json())
        .then((user) => {
            $('#editUserId').val(user.id)
            $('#editUserName').val(user.name)
            $('#editUserSurname').val(user.surname)
            $('#editUserAge').val(user.age)
            $('#editUserEmail').val(user.email)
        });


    editButton.onclick = function () {
        let listRoles = Array.from(document.getElementById('selectEditRole').selectedOptions).map(role => role.value);

        const updated = fetch('http://localhost:8080/api/users', {
                method: "PUT",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json;charset=UTF-8'
                    },
                     body: JSON.stringify({
                         id: editUserForm.id.value,
                         name: editUserForm.name.value,
                         surname: editUserForm.surname.value,
                         age: editUserForm.age.value,
                         email: editUserForm.email.value,
                         password: editUserForm.password.value,
                         roles: listRoles
                    })
                 }).then(response => console.log(response));

       // $("#closeButtonInEditModal").click();
    }



    closeButtonHeader.onclick = function () {
        modalEdit.style.display = "none";
    }

    closeButton.onclick = function () {
        modalEdit.style.display = "none";
    }
}






function getUserForDeleteModal(id) {
    let closeButton = document.getElementById("closeButtonInDeleteModal");
    let deleteButton = document.getElementById("deleteButtonInDeleteModal");
    let modalDelete = document.getElementById("deleteUserModal");
    let closeButtonHeader = document.getElementById("closeButtonInDeleteModalHeader");

    fetch('http://localhost:8080/api/users/' + id)
        .then(response => response.json())
        .then((user) => {
            $('#deleteUserId').val(user.id)
            $('#deleteUserName').val(user.name)
            $('#deleteUserSurname').val(user.surname)
            $('#deleteUserAge').val(user.age)
            $('#deleteUserEmail').val(user.email)

        });

    deleteButton.onclick = function () {
        fetch('http://localhost:8080/api/users/' + id, {
            method: 'DELETE'
        });
    }

    closeButton.onclick = function () {
        modalDelete.style.display = "none";
    }

    closeButtonHeader.onclick = function () {
        modalDelete.style.display = "none";

    }

    window.onclick = function (event) {
        if (event.target == modalDelete) {
            modalDelete.style.display = "none";
        }
    }
}