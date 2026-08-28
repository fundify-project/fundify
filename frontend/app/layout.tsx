import type { Metadata } from "next";
import localFont from "next/font/local";
import "./globals.css";
import Header from "@/components/common/Header";

const pretendard = localFont({
  src: "../public/fonts/PretendardVariable.woff2",
  display: "swap",
  variable: "--font-pretendard",
  weight: "45 920",
});

export const metadata: Metadata = {
  title: "Fundify",
  description: "흩어진 기업 정보를 한 화면에서 비교하다",
};

export default function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="ko" className={pretendard.variable} suppressHydrationWarning>
      <body
        className="min-h-screen bg-ink font-sans antialiased"
        suppressHydrationWarning
      >
        <Header />
        {children}
      </body>
    </html>
  );
}
