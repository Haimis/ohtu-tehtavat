package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {
    
        
    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa k;
    
    @Before
    public void setUp() {
        // luodaan ensin mock-oliot
        pankki = mock(Pankki.class);        
        varasto = mock(Varasto.class);
        viite = mock(Viitegeneraattori.class);
        // sitten testattava kauppa 
        
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        
        k = new Kauppa(varasto, pankki, viite);     
        k.aloitaAsiointi();
        
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        when(varasto.saldo(2)).thenReturn(1);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kauramaito", 3));
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {

        // tehdään ostokset
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());   
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }
    
        
    @Test
    public void kutsutaanTilisiirtoaOikeillaParametreilla() {
        k.lisaaKoriin(1);
        k.tilimaksu("james", "007");
        
        verify(pankki).tilisiirto(eq("james"), anyInt(), eq("007"), anyString(), eq(5));           
        
    }
        
    @Test
    public void kutsutaanTilisiirtoaOikeillaParametreillaKunUseampiTuote() {
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("james", "007");
        
        verify(pankki).tilisiirto(eq("james"), anyInt(), eq("007"), anyString(), eq(8));
        
    }
    
    @Test
    public void kutsutaanTilisiirtoaOikeillaParametreillaKunUseampiKappaleSamaaTuotetta() {
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);      
        k.tilimaksu("james", "007");
        
        verify(pankki).tilisiirto(eq("james"), anyInt(), eq("007"), anyString(), eq(10));
    }
    
    @Test
    public void kutsutaanTilisiirtoaOikeillaParametreillaKunUseampiTuoteJoistaJokinLopppu() {
        when(varasto.saldo(3)).thenReturn(0);
        when(varasto.haeTuote(3)).thenReturn(new Tuote(3, "mantelimaito", 4));
        
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.lisaaKoriin(3);
        k.tilimaksu("james", "007");
        
        verify(pankki).tilisiirto(eq("james"), anyInt(), eq("007"), anyString(), eq(8));
    }
    
    @Test
    public void aloitaAsiointiNollaaOstokset() {
        k.lisaaKoriin(1);
        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("james", "007");
        
        verify(pankki).tilisiirto(eq("james"), anyInt(), eq("007"), anyString(), eq(3));
    }
    
    @Test
    public void ainaUusiViitenumero() {
        when(viite.uusi()).
        thenReturn(1).
        thenReturn(2);
        
        k.lisaaKoriin(1);
        k.tilimaksu("james", "007");
        verify(pankki).tilisiirto(eq("james"), eq(1), eq("007"), anyString(), eq(5));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("jerry", "cotton");
        verify(pankki).tilisiirto(eq("jerry"), eq(2), eq("cotton"), anyString(), eq(5));
        
    }
    
        
    @Test
    public void koristaPoistaminenKutsuuPalautusta() {
        
        k.lisaaKoriin(1);
        k.poistaKorista(1);
        Tuote t = varasto.haeTuote(1);
        verify(varasto, times(1)).palautaVarastoon(t);
        
    }

}
