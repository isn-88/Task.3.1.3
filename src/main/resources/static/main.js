


function editModalContent(t, id, userName, userSurname, userAge, userEmail, userPassword) {

    let modal = $('#editUserModal')

    // change modal content
    modal.find('#userId').val(id)
    modal.find('#userName').val(userName)
    modal.find('#userSurname').val(userSurname)
    modal.find('#userAge').val(userAge)
    modal.find('#userEmail').val(userEmail)
    //modal.find('#userPassword').val(userPassword)
}