package com.movieswipe.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.movieswipe.model.Group

@Composable
fun GroupManagementScreen(
    groups: List<Group>,
    onCreateGroup: (String) -> Unit,
    onJoinGroup: (String) -> Unit,
    onDeleteGroup: (String) -> Unit,
    onSelectGroup: (Group) -> Unit
) {
    var groupName by remember { mutableStateOf("") }
    var inviteCode by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Your Groups", style = MaterialTheme.typography.h6)
        groups.forEach { group ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(group.name)
                Row {
                    Button(onClick = { onSelectGroup(group) }) { Text("Open") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onDeleteGroup(group.id) }) { Text("Delete") }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = groupName, onValueChange = { groupName = it }, label = { Text("New Group Name") })
        Button(onClick = { onCreateGroup(groupName) }, enabled = groupName.isNotBlank()) { Text("Create Group") }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = inviteCode, onValueChange = { inviteCode = it }, label = { Text("Invite Code") })
        Button(onClick = { onJoinGroup(inviteCode) }, enabled = inviteCode.isNotBlank()) { Text("Join Group") }
    }
}
