package com.tugas.unscollab.ui.components.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.button.SecondaryButton
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    isBottomSheetOpen: Boolean,
    onDismiss: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    CustomFilter: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isBottomSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp
            ),
            modifier = modifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Filter"
                    )

                    CustomFilter()

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SecondaryButton(
                            text = "Reset",
                            onButtonClick = {},
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                        )

                        PrimaryButton(
                            text = "Apply",
                            onButtonClick = {},
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewCustomBottomSheet() {
    UNSCollabTheme {
        CustomBottomSheet(
            isBottomSheetOpen = true,
            onDismiss = {  },
            CustomFilter = { }
        )
    }
}