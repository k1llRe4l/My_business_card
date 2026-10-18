package com.example.businesscard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

private val Ink = Color(0xFF09111F)
private val Card = Color(0xCC111D31)
private val Muted = Color(0xFFB8C5D9)
private val Accent = Color(0xFFFF00CE)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(9, 17, 31)
        window.navigationBarColor = android.graphics.Color.rgb(9, 17, 31)
        setContent {
            BusinessCardTheme {
                BusinessCard(
                    onPhone = {
                        val phone = getString(R.string.phone)
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Uri.encode(phone)}"))
                        open(intent)
                    },
                    onEmail = {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${getString(R.string.email)}"))
                        open(intent)
                    },
                    onWebsite = {
                        val address = getString(R.string.github).let {
                            if (it.startsWith("http://") || it.startsWith("https://")) it else "https://$it"
                        }
                        open(Intent(Intent.ACTION_VIEW, Uri.parse(address)))
                    },
                    onTelegram = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://${getString(R.string.telegram)}"))
                        open(intent)
                    }
                )
            }
        }
    }

    private fun open(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@Composable
private fun BusinessCard(onPhone: () -> Unit, onEmail: () -> Unit, onWebsite: () -> Unit, onTelegram: () -> Unit) {
    Box(Modifier.fillMaxSize().background(Ink)) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0x80071020), Color(0xB309111F), Color(0xCC09111F)))))

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp, vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                color = Card,
                border = androidx.compose.foundation.BorderStroke(1.dp, Accent.copy(alpha = 0.2f)),
                shadowElevation = 18.dp
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(R.string.card_eyebrow), color = Accent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 2.2.sp)
                    Spacer(Modifier.height(20.dp))

                    Box(contentAlignment = Alignment.Center) {
                        Surface(shape = CircleShape, color = Color(0xFF22314A), modifier = Modifier.size(100.dp), border = androidx.compose.foundation.BorderStroke(2.dp, Accent)) {
                            Image(painterResource(R.drawable.me), contentDescription = stringResource(R.string.photo_description, stringResource(R.string.name)), contentScale = ContentScale.Crop)
                        }
                    }

                    Spacer(Modifier.height(15.dp))
                    Text(stringResource(R.string.name), color = Color.White, fontSize = 28.sp, lineHeight = 34.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(5.dp))
                    Text(stringResource(R.string.title), color = Accent, fontSize = 14.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(22.dp))

                    Surface(shape = RoundedCornerShape(20.dp), color = Color.White, modifier = Modifier.width(184.dp).aspectRatio(1f)) {
                        Image(painterResource(R.drawable.qr_code), contentDescription = stringResource(R.string.qr_description, stringResource(R.string.name)), contentScale = ContentScale.Fit, modifier = Modifier.padding(11.dp))
                    }
                    Spacer(Modifier.height(9.dp))
                    Text(stringResource(R.string.scan_to_connect), color = Muted, fontSize = 11.sp, letterSpacing = 0.5.sp)
                    Spacer(Modifier.height(24.dp))

//                    ContactRow("☎", stringResource(R.string.phone), onPhone)
//                    Spacer(Modifier.height(10.dp))
//                    ContactRow("✉", stringResource(R.string.email), onEmail)
//                    Spacer(Modifier.height(10.dp))
//                    ContactRow("➤", stringResource(R.string.telegram), onTelegram)
//                    Spacer(Modifier.height(10.dp))
//                    ContactRow("↗", stringResource(R.string.github), onWebsite)
                    ContactRow(Icons.Default.Phone, stringResource(R.string.phone), onPhone)
                    Spacer(Modifier.height(10.dp))
                    ContactRow(Icons.Default.Email, stringResource(R.string.email), onEmail)
                    Spacer(Modifier.height(10.dp))
                    ContactRow(Icons.AutoMirrored.Filled.Send, stringResource(R.string.telegram), onTelegram)
                    Spacer(Modifier.height(10.dp))
                    ContactRow(Icons.Default.Language, stringResource(R.string.github), onWebsite) // или Icons.Default.OpenInNew
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ContactRow(icon: ImageVector, value: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1C2A40),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x22FFFFFF))
    ) {
        Row(Modifier.padding(horizontal = 14.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(34.dp).clip(RoundedCornerShape(11.dp)).background(Accent.copy(alpha = 0.13f)), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Accent,
                    modifier = Modifier.size(18.dp) // Размер самой иконки внутри бокса
                )
            }
            Spacer(Modifier.width(12.dp))
            Text(value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium, maxLines = 1)
        }
    }
}
