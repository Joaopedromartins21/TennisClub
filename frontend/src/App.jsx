import { useState, useEffect } from 'react'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import { Button } from '@/components/ui/button.jsx'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card.jsx'
import { Badge } from '@/components/ui/badge.jsx'
import { Calendar, Clock, MapPin, Users, Trophy, Star, ChevronRight, Phone, Mail, MapPinIcon } from 'lucide-react'
import './App.css'

// Componente Header
function Header() {
  return (
    <header className="bg-white shadow-sm border-b">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <div className="flex items-center space-x-2">
            <Trophy className="h-8 w-8 text-green-600" />
            <div>
              <h1 className="text-xl font-bold text-gray-900">TennisClub</h1>
              <p className="text-xs text-gray-500">by João Pedro Aguiar</p>
            </div>
          </div>
          <nav className="hidden md:flex space-x-8">
            <a href="#home" className="text-gray-700 hover:text-green-600 transition-colors">Início</a>
            <a href="#courts" className="text-gray-700 hover:text-green-600 transition-colors">Quadras</a>
            <a href="#booking" className="text-gray-700 hover:text-green-600 transition-colors">Agendamentos</a>
            <a href="#contact" className="text-gray-700 hover:text-green-600 transition-colors">Contato</a>
          </nav>
          <div className="flex space-x-2">
            <Button variant="outline" size="sm">Login</Button>
            <Button size="sm" className="bg-green-600 hover:bg-green-700">Cadastrar</Button>
          </div>
        </div>
      </div>
    </header>
  )
}

// Componente Hero Section
function HeroSection() {
  return (
    <section id="home" className="bg-gradient-to-br from-green-50 to-blue-50 py-20">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center">
          <h1 className="text-4xl md:text-6xl font-bold text-gray-900 mb-6">
            Bem-vindo ao <span className="text-green-600">TennisClub</span>
          </h1>
          <p className="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
            Sistema completo de agendamento de quadras de tênis. Desenvolvido por João Pedro Aguiar 
            como projeto de portfólio, demonstrando expertise em Java Spring Boot e React.
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Button size="lg" className="bg-green-600 hover:bg-green-700">
              <Calendar className="mr-2 h-5 w-5" />
              Agendar Quadra
            </Button>
            <Button variant="outline" size="lg">
              <Users className="mr-2 h-5 w-5" />
              Ver Quadras Disponíveis
            </Button>
          </div>
        </div>
      </div>
    </section>
  )
}

// Componente Features
function FeaturesSection() {
  const features = [
    {
      icon: Calendar,
      title: "Agendamento Fácil",
      description: "Sistema intuitivo para agendar quadras com verificação de disponibilidade em tempo real."
    },
    {
      icon: Clock,
      title: "Horários Flexíveis",
      description: "Funcionamento das 6h às 22h com slots de 1 hora para máxima flexibilidade."
    },
    {
      icon: MapPin,
      title: "Múltiplas Quadras",
      description: "5 quadras disponíveis com diferentes características e preços."
    },
    {
      icon: Users,
      title: "Gestão de Usuários",
      description: "Sistema completo de cadastro e gerenciamento de clientes e administradores."
    }
  ]

  return (
    <section className="py-20 bg-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-3xl font-bold text-gray-900 mb-4">Funcionalidades do Sistema</h2>
          <p className="text-lg text-gray-600">Desenvolvido com as melhores práticas de desenvolvimento</p>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          {features.map((feature, index) => (
            <Card key={index} className="text-center hover:shadow-lg transition-shadow">
              <CardHeader>
                <feature.icon className="h-12 w-12 text-green-600 mx-auto mb-4" />
                <CardTitle className="text-lg">{feature.title}</CardTitle>
              </CardHeader>
              <CardContent>
                <CardDescription>{feature.description}</CardDescription>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  )
}

// Componente Courts
function CourtsSection() {
  const courts = [
    {
      id: 1,
      name: "Quadra Central",
      description: "Quadra principal com arquibancada e iluminação profissional",
      price: "R$ 80,00/hora",
      features: ["Iluminação LED", "Arquibancada", "Piso Sintético"],
      status: "Disponível"
    },
    {
      id: 2,
      name: "Quadra Norte",
      description: "Quadra coberta ideal para jogos em qualquer clima",
      price: "R$ 70,00/hora",
      features: ["Cobertura", "Climatizada", "Som Ambiente"],
      status: "Disponível"
    },
    {
      id: 3,
      name: "Quadra Sul",
      description: "Quadra ao ar livre com vista panorâmica",
      price: "R$ 60,00/hora",
      features: ["Vista Panorâmica", "Ar Livre", "Vestiário"],
      status: "Ocupada"
    }
  ]

  return (
    <section id="courts" className="py-20 bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-3xl font-bold text-gray-900 mb-4">Nossas Quadras</h2>
          <p className="text-lg text-gray-600">Escolha a quadra ideal para seu jogo</p>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {courts.map((court) => (
            <Card key={court.id} className="hover:shadow-lg transition-shadow">
              <CardHeader>
                <div className="flex justify-between items-start">
                  <div>
                    <CardTitle className="text-xl">{court.name}</CardTitle>
                    <CardDescription className="mt-2">{court.description}</CardDescription>
                  </div>
                  <Badge variant={court.status === 'Disponível' ? 'default' : 'secondary'}>
                    {court.status}
                  </Badge>
                </div>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  <div className="text-2xl font-bold text-green-600">{court.price}</div>
                  <div className="space-y-2">
                    {court.features.map((feature, index) => (
                      <div key={index} className="flex items-center text-sm text-gray-600">
                        <Star className="h-4 w-4 text-yellow-500 mr-2" />
                        {feature}
                      </div>
                    ))}
                  </div>
                  <Button className="w-full bg-green-600 hover:bg-green-700" disabled={court.status !== 'Disponível'}>
                    {court.status === 'Disponível' ? 'Agendar Agora' : 'Indisponível'}
                    <ChevronRight className="ml-2 h-4 w-4" />
                  </Button>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  )
}

// Componente Booking
function BookingSection() {
  return (
    <section id="booking" className="py-20 bg-white">
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-3xl font-bold text-gray-900 mb-4">Sistema de Agendamento</h2>
          <p className="text-lg text-gray-600">API REST completa desenvolvida em Java Spring Boot</p>
        </div>
        
        <Card className="p-8">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div>
              <h3 className="text-xl font-semibold mb-4">Tecnologias Utilizadas</h3>
              <div className="space-y-3">
                <div className="flex items-center">
                  <Badge variant="outline" className="mr-3">Backend</Badge>
                  <span>Java 17 + Spring Boot 3</span>
                </div>
                <div className="flex items-center">
                  <Badge variant="outline" className="mr-3">Database</Badge>
                  <span>MySQL + JPA/Hibernate</span>
                </div>
                <div className="flex items-center">
                  <Badge variant="outline" className="mr-3">Frontend</Badge>
                  <span>React 18 + Tailwind CSS</span>
                </div>
                <div className="flex items-center">
                  <Badge variant="outline" className="mr-3">API</Badge>
                  <span>REST + Swagger/OpenAPI</span>
                </div>
              </div>
            </div>
            
            <div>
              <h3 className="text-xl font-semibold mb-4">Funcionalidades da API</h3>
              <div className="space-y-2 text-sm text-gray-600">
                <div>• Gerenciamento completo de usuários (CRUD)</div>
                <div>• Cadastro e controle de quadras</div>
                <div>• Sistema de agendamentos com validações</div>
                <div>• Verificação de disponibilidade em tempo real</div>
                <div>• Autenticação e autorização (Spring Security)</div>
                <div>• Documentação automática (Swagger UI)</div>
                <div>• Testes unitários e de integração</div>
              </div>
            </div>
          </div>
          
          <div className="mt-8 pt-8 border-t">
            <div className="text-center">
              <p className="text-sm text-gray-500 mb-4">
                Base URL da API: <code className="bg-gray-100 px-2 py-1 rounded">/api/joaopedroaguiar/tennisclub/v1</code>
              </p>
              <Button variant="outline" className="mr-4">
                Ver Documentação da API
              </Button>
              <Button className="bg-green-600 hover:bg-green-700">
                Testar Endpoints
              </Button>
            </div>
          </div>
        </Card>
      </div>
    </section>
  )
}

// Componente Footer
function Footer() {
  return (
    <footer id="contact" className="bg-gray-900 text-white py-16">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div>
            <div className="flex items-center space-x-2 mb-4">
              <Trophy className="h-8 w-8 text-green-400" />
              <div>
                <h3 className="text-xl font-bold">TennisClub</h3>
                <p className="text-sm text-gray-400">by João Pedro Aguiar</p>
              </div>
            </div>
            <p className="text-gray-400 mb-4">
              Sistema completo de agendamento de quadras de tênis desenvolvido como projeto de portfólio, 
              demonstrando expertise em desenvolvimento full-stack.
            </p>
          </div>
          
          <div>
            <h4 className="text-lg font-semibold mb-4">Tecnologias</h4>
            <div className="space-y-2 text-gray-400">
              <div>• Java 17 + Spring Boot 3</div>
              <div>• React 18 + Tailwind CSS</div>
              <div>• MySQL + JPA/Hibernate</div>
              <div>• REST API + Swagger</div>
              <div>• Spring Security</div>
              <div>• Maven + Git</div>
            </div>
          </div>
          
          <div>
            <h4 className="text-lg font-semibold mb-4">Contato</h4>
            <div className="space-y-3">
              <div className="flex items-center">
                <Mail className="h-5 w-5 text-green-400 mr-3" />
                <span className="text-gray-400">joaopedroaguiar@example.com</span>
              </div>
              <div className="flex items-center">
                <Phone className="h-5 w-5 text-green-400 mr-3" />
                <span className="text-gray-400">+55 (11) 99999-9999</span>
              </div>
              <div className="flex items-center">
                <MapPinIcon className="h-5 w-5 text-green-400 mr-3" />
                <span className="text-gray-400">GitHub: Joaopedromartins21</span>
              </div>
            </div>
          </div>
        </div>
        
        <div className="border-t border-gray-800 mt-12 pt-8 text-center">
          <p className="text-gray-400">
            © 2024 TennisClub - Desenvolvido por João Pedro Aguiar. Projeto de Portfólio.
          </p>
        </div>
      </div>
    </footer>
  )
}

// Componente principal da aplicação
function App() {
  return (
    <div className="min-h-screen bg-white">
      <Header />
      <main>
        <HeroSection />
        <FeaturesSection />
        <CourtsSection />
        <BookingSection />
      </main>
      <Footer />
    </div>
  )
}

export default App

